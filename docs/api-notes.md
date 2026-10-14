# Atelier 5 — Notes : API REST, Postman et Swagger

Application : `http://localhost:8089` — préfixe `/api` défini une seule fois par `server.servlet.context-path=/api`.
Swagger UI : `http://localhost:8089/api/swagger-ui.html` — description OpenAPI : `http://localhost:8089/api/v3/api-docs`.
Collection Postman : `docs/AutoLoc.postman_collection.json`, environnement `docs/local.postman_environment.json`.

## A. Endpoints

Tous les contrôleurs sont dans `web.controller`, dépendent de l'interface `I…Service` (champ `final`, constructeur Lombok) et ne touchent jamais au repository.

| Contrôleur | Verbe et route | Service appelé | Succès | Erreurs |
|---|---|---|---|---|
| `<Entité>RestController` (Agence, Employe, Vehicule, Equipement, Client, Reservation, Contrat, Maintenance) | GET `/api/<entités>` | `findAll()` | 200 | — |
| idem | GET `/api/<entités>/{id}` | `findById(id)` | 200 | 404 |
| idem | POST `/api/<entités>` | `create(entité)` | 201 | 400 |
| idem | PUT `/api/<entités>/{id}` | `update(id, entité)` | 200 | 404, 400 |
| idem | DELETE `/api/<entités>/{id}` | `deleteById(id)` | 204 | 404 |
| `PaiementRestController` | GET `/api/paiements` | `findAll()` | 200 | — |
| `PaiementRestController` | GET `/api/paiements/{id}` | `findById(id)` | 200 | 404 |
| `PaiementRestController` | POST / PUT / DELETE | aucun | — | 405 |

Les erreurs sont traduites par `GlobalExceptionHandler` : `ResourceNotFoundException` → 404, `IllegalArgumentException` et JSON illisible → 400, avec un corps `statut`, `message`, `horodatage`.

## B. Annotations Jackson

| Association | Annotation | Justification |
|---|---|---|
| `Contrat.paiements` / `Paiement.contrat` | `@JsonManagedReference("contrat-paiements")` / `@JsonBackReference("contrat-paiements")` | Vraie composition (`cascade = ALL`) : les paiements se créent dans le JSON du contrat, et le lien retour est reconstruit |
| `Agence.vehicules`, `Agence.employes` | `@JsonIgnore` | Côté inverse : l'enfant désigne son agence par un identifiant, la liste ne sert pas à la lecture |
| `Equipement.vehicules` | `@JsonIgnore` | Côté inverse du `@ManyToMany` |
| `Client.reservations` | `@JsonIgnore` | Côté inverse |
| `Reservation.contrat` | `@JsonIgnore` | Côté inverse du `@OneToOne` |
| Associations `@ManyToOne` propriétaires (`Vehicule.agence`, `Employe.agence`, `Reservation.client`, `Reservation.vehicule`, `Maintenance.vehicule`, `Contrat.reservation`) | aucune | L'enfant désigne son parent : `{ "agence": { "idAgence": 1 } }` |

`@JsonBackReference` fait ignorer la propriété à la lecture **et** à l'écriture : le parent n'est donc jamais lu dans le JSON de l'enfant. `@JsonIgnore` ignore simplement la propriété dans les deux sens. On ne peut donc pas mettre `@JsonBackReference` sur `Reservation.client` : le POST créerait une réservation sans client.

Palliatif provisoire pour les proxys LAZY Hibernate : `spring.jackson.serialization.fail-on-empty-beans=false` (le JSON contient alors `"hibernateLazyInitializer": {}`). Il disparaîtra avec les DTO (Chapitre 10).

## C. Postman et Swagger

- **Postman** sert à **tester** : requêtes rejouables, assertions sur le statut, variables d'environnement. Exemple rencontré : un POST sans corps donnait un 400 « Required request body is missing » ; un `categorie` invalide (`ECONOMIQUE`) donnait un 400 listant les valeurs acceptées ; une agence manquante donnait un 500 (`Vehicule.agence` non nulle) ; une immatriculation en double donnait un 500 (contrainte d'unicité).
- **Swagger UI** sert à **documenter et explorer** : le contrat est généré par SpringDoc à partir du code, donc toujours synchronisé. Exemple : les groupes « Contrats » et « Paiements », les résumés et les codes de réponse (200, 201, 400, 404) apparaissent grâce à `@Tag`, `@Operation` et `@ApiResponse`.

## D. Anomalies SonarQube for IDE

| Règle | Explication | Correction |
|---|---|---|
| java:S1710 | « Remove the 'ApiResponses' wrapper from this annotation group » : `@ApiResponse` est répétable depuis Java 8, le conteneur `@ApiResponses({...})` est superflu (30 occurrences) | Suppression du conteneur : les `@ApiResponse` sont écrits les uns sous les autres, et l'import `ApiResponses` est retiré |
| java:S4684 | « Replace this persistent entity with a simple POJO or DTO object » (17 occurrences) : une entité JPA est utilisée comme paramètre ou valeur de retour d'un endpoint, ce qui expose le modèle de la base et ses champs internes | Non corrigée volontairement : l'énoncé indique que l'exposition directe des entités est provisoire. Elle sera résolue au Chapitre 10 avec les DTO |

## E. Questions de compréhension

1. **Quel composant reçoit les requêtes ?** Le `DispatcherServlet`. Il interroge ses `HandlerMapping` : ceux-ci comparent le verbe et l'URL aux annotations `@RequestMapping`, `@GetMapping`… et retrouvent la méthode du contrôleur. La valeur retournée est ensuite convertie en JSON.
2. **Pourquoi dépendre de `IContratService` ?** Le contrôleur reste mince : il ne connaît que le contrat du service (abstraction), jamais le repository (la logique métier est dans le service) ni l'implémentation (remplaçable sans modifier le contrôleur).
3. **201, 204, 404.** 201 Created : une ressource a été créée. 204 No Content : l'opération a réussi et il n'y a rien à renvoyer. Une ressource absente est une erreur du client, pas du serveur : `ResourceNotFoundException` est traduite en 404 par le `GlobalExceptionHandler`, sinon on obtiendrait un 500.
4. **Paiement en lecture seule.** Les paiements se créent, se modifient et se suppriment à travers le contrat (cascade). Un POST sur `/api/paiements` reçoit un **405 Method Not Allowed** : aucune méthode du contrôleur ne gère ce verbe.
5. **Entités exposées.** Risques : couplage entre l'API et la base, champs internes visibles, boucles de sérialisation, proxys LAZY, modification involontaire de colonnes. `Contrat.paiements` est une composition : les paiements naissent avec le contrat, donc `@JsonManagedReference` / `@JsonBackReference`. `Agence.vehicules` est le côté inverse : un véhicule se crée seul en désignant son agence, donc un simple `@JsonIgnore` suffit.
