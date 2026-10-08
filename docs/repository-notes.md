# Atelier 3 — Notes sur la couche Repository

## Choix des interfaces

Les neuf repositories sont dans le package `tn.esprit.raniaselmi_4cce10.repository`, préfixés par `I`,
et étendent tous `JpaRepository<Entité, Long>` : CRUD complet, `findAll` renvoie une `List`,
tri, pagination, `flush` / `saveAndFlush`.

| Interface | Étend | Justification |
|---|---|---|
| IContratRepository | JpaRepository<Contrat, Long> | CRUD complet, findAll renvoie une List, saveAndFlush disponible. |
| IPaiementRepository | JpaRepository<Paiement, Long> | Lecture des paiements ; création/suppression via le Contrat (cascade + orphanRemoval). |
| IAgenceRepository | JpaRepository<Agence, Long> | Même interface complète pour toutes les entités, pour rester homogène. |
| IEmployeRepository | JpaRepository<Employe, Long> | Idem. |
| IVehiculeRepository | JpaRepository<Vehicule, Long> | Idem. |
| IEquipementRepository | JpaRepository<Equipement, Long> | Idem. |
| IClientRepository | JpaRepository<Client, Long> | Idem. |
| IReservationRepository | JpaRepository<Reservation, Long> | Idem. |
| IMaintenanceRepository | JpaRepository<Maintenance, Long> | Idem. |

Attention : `deleteAllInBatch` / `deleteAllByIdInBatch` contournent le contexte de persistance,
donc la cascade et l'`orphanRemoval` ne s'appliquent pas (risque de paiements orphelins).

## Anomalies SonarQube for IDE

Anomalies relevées par relecture du code selon les règles de SonarQube for IDE, puis corrigées.

| Anomalie | Règle / explication | Correction apportée |
|---|---|---|
| `import jakarta.persistence.*;` dans 9 entités (Agence, Client, Contrat, Employe, Equipement, Maintenance, Paiement, Reservation, Vehicule) | java:S2208 — les imports avec joker sont à éviter : on ne voit pas quelles classes sont réellement utilisées et deux packages peuvent entrer en conflit. | Remplacés par des imports explicites (`jakarta.persistence.Entity`, `Id`, `Column`, …). |
| Lignes vides en double dans `Client.java` et `Vehicule.java` | Mauvaise lisibilité / code non conforme au formatage standard (Clean Code). | Lignes vides superflues supprimées. |
| Espace en fin de ligne après `@Service` dans `VehiculeServiceImpl.java` | Espace superflu en fin de ligne (formatage, Clean Code). | Espace supprimé. |
