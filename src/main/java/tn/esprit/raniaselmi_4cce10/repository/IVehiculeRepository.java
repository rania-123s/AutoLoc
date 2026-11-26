package tn.esprit.raniaselmi_4cce10.repository;

import java.math.BigDecimal;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.raniaselmi_4cce10.domain.CategorieVehicule;
import tn.esprit.raniaselmi_4cce10.domain.StatutVehicule;
import tn.esprit.raniaselmi_4cce10.domain.Vehicule;

public interface IVehiculeRepository extends JpaRepository<Vehicule, Long> {

    // K1 — véhicules d'une marque
    List<Vehicule> findByMarque(String marque);

    // K2 — véhicules d'une catégorie ET d'un statut
    List<Vehicule> findByCategorieAndStatut(CategorieVehicule categorie, StatutVehicule statut);

    // K3 — tarif entre min et max, triés par tarif croissant
    List<Vehicule> findByTarifJournalierBetweenOrderByTarifJournalierAsc(BigDecimal min, BigDecimal max);

    // K4 — modèle contenant un mot-clé, sans tenir compte de la casse
    List<Vehicule> findByModeleContainingIgnoreCase(String motCle);

    // K5 — véhicules appartenant à une liste de catégories
    List<Vehicule> findByCategorieIn(List<CategorieVehicule> categories);

    // K6 — une immatriculation existe-t-elle déjà ?
    boolean existsByImmatriculation(String immatriculation);

    // K7 — nombre de véhicules dans un statut
    long countByStatut(StatutVehicule statut);

    // K8 — les 3 véhicules les moins chers
    List<Vehicule> findTop3ByOrderByTarifJournalierAsc();

    // K9 — véhicules d'une agence (par son id) : vehicule.agence.idAgence
    List<Vehicule> findByAgenceIdAgence(Long agenceId);

    // K10 — véhicules sans agence
    List<Vehicule> findByAgenceIsNull();
}
