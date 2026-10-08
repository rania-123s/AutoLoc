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

À compléter après l'analyse du projet dans IntelliJ (clic droit sur le projet → *Analyze with SonarQube for IDE*).

| Anomalie | Règle / explication | Correction apportée |
|---|---|---|
| à compléter | à compléter | à compléter |
| à compléter | à compléter | à compléter |
| à compléter | à compléter | à compléter |
