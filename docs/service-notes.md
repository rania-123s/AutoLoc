# Atelier 4 — Notes : IoC, DI et couche Service

## A. Modes d'injection comparés

| Critère | Constructeur (`@RequiredArgsConstructor` + `final`) | Attribut (`@Autowired` sur le champ) |
|---|---|---|
| Champ `final` possible ? | Oui | Non |
| Dépendance visible dans la signature de la classe ? | Oui (paramètre du constructeur) | Non (dépendance cachée) |
| Utilisable avec `new` hors conteneur sans dépendance `null` ? | Oui : impossible de construire l'objet sans fournir le repository | Non : le champ reste `null` hors conteneur |
| Signalé par SonarQube for IDE ? | Non | Oui (règle S6813 – *Remove this field injection and use constructor injection instead*) |

## B. Services

| Service | Dépendances injectées | Mode | Justification |
|---|---|---|---|
| AgenceServiceImpl | IAgenceRepository | Constructeur | Champ `final`, interface, service sans état ; nom obligatoire |
| EmployeServiceImpl | IEmployeRepository | Constructeur | Idem ; nom, prénom et rôle obligatoires |
| VehiculeServiceImpl | IVehiculeRepository | Constructeur | Idem ; immatriculation obligatoire, tarif journalier non négatif |
| EquipementServiceImpl | IEquipementRepository | Constructeur | Idem ; libellé obligatoire |
| ClientServiceImpl | IClientRepository | Constructeur | Idem ; nom et e-mail obligatoires |
| ReservationServiceImpl | IReservationRepository | Constructeur | Idem ; date de fin non antérieure à la date de début |
| ContratServiceImpl | IContratRepository | Constructeur | Idem ; montant total non négatif ; suppression en cascade des paiements |
| PaiementServiceImpl | IPaiementRepository | Constructeur | Idem ; lecture seule (`findById`, `findAll`) |
| MaintenanceServiceImpl | IMaintenanceRepository | Constructeur | Idem ; date de fin non antérieure à la date de début |

## C. Messages d'erreur du conteneur

**Message 1 — `IContratService` introuvable (`NoSuchBeanDefinitionException`)**
Bean concerné : `IContratService`, demandé par le constructeur de `ContratController`.
Causes probables : (1) `@Service` oublié sur `ContratServiceImpl` ; (2) classe placée hors du package racine scanné (`tn.esprit.raniaselmi_4cce10`).
Correction : ajouter `@Service` et/ou déplacer la classe dans un sous-package du package racine.

**Message 2 — deux beans trouvés (`NoUniqueBeanDefinitionException`)**
Bean concerné : le paramètre 0 du constructeur de `NotificationController` ; candidats `emailNotificateur` et `smsNotificateur`.
Correction 1 : `@Primary` **sur la classe** du bean à privilégier (ex. `EmailNotificateur`).
Correction 2 : `@Qualifier("emailNotificateur")` **sur le paramètre du constructeur** du contrôleur.

**Message 3 — dépendance circulaire (`BeanCurrentlyInCreationException`)**
`clientServiceImpl` et `reservationServiceImpl` s'injectent mutuellement. Avec l'injection par constructeur, chaque bean exige l'autre déjà créé pour pouvoir être instancié : aucun ne peut être construit en premier, le cycle est donc impossible à contourner.
Piste de conception : extraire la responsabilité commune dans un troisième service (ou un composant) dont dépendent les deux, ou ne faire dépendre qu'un seul sens (la réservation connaît le client, pas l'inverse).

## D. Anomalies SonarQube for IDE

| Règle | Explication | Correction |
|---|---|---|
| java:S6813 | Injection par attribut (`@Autowired` sur un champ) : champ non `final`, dépendance cachée, `null` hors conteneur (« Remove this field injection and use constructor injection instead ») | Retour à l'injection par constructeur (`@RequiredArgsConstructor` + champ `final`) |
| java:S1128 | Import inutile : `java.util.Map` n'est jamais utilisé | Suppression de l'import |
| java:S112 | Exception générique : `throw new RuntimeException(...)` ne permet pas à l'appelant de distinguer l'erreur (« Replace generic exceptions with specific library exceptions or a custom exception ») | Utilisation de `ResourceNotFoundException`, exception métier dédiée |

Après correction, l'analyse des packages `service` et `exception` ne signale plus aucune anomalie.

## E. Questions de compréhension

1. **Où est le `new` de `ContratServiceImpl` ?** Dans le conteneur Spring : au démarrage, le scan détecte `@Service`, puis `ApplicationContext` appelle le constructeur en lui fournissant le proxy de `IContratRepository`. Aucun `new` n'est écrit dans notre code.
2. **Pourquoi dépendre de `IContratService` et non de `ContratServiceImpl` ?** Pour dépendre d'une abstraction : on peut changer ou remplacer l'implémentation (ou fournir une doublure) sans modifier le contrôleur.
3. **Pourquoi un singleton doit-il rester sans état ?** Il est partagé par tous les threads. Exemple de bug : un attribut `contratCourant` modifié par une requête A est écrasé par la requête B ; A traite alors le contrat de B.
4. **Pourquoi `update` charge-t-il l'existant ?** Passer directement l'objet reçu à `save` écraserait toutes les colonnes (valeurs absentes = `null`) et, avec `orphanRemoval`, pourrait vider la collection de paiements ; on recopie donc uniquement les attributs modifiables.
5. **Différences.** `@Component` se pose sur une classe détectée par le scan, `@Bean` sur une méthode de `@Configuration` (classe non annotable ou construction particulière). `@Primary` est posé sur la classe candidate par défaut, `@Qualifier` sur le point d'injection pour un choix explicite. `IPaiementService` est en lecture seule car les paiements sont créés, modifiés et supprimés à travers le `Contrat` (cascade).
