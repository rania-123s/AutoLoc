package tn.esprit.raniaselmi_4cce10.service;

import java.math.BigDecimal;
import java.util.List;
import tn.esprit.raniaselmi_4cce10.domain.CategorieVehicule;
import tn.esprit.raniaselmi_4cce10.domain.StatutVehicule;
import tn.esprit.raniaselmi_4cce10.domain.Vehicule;
import tn.esprit.raniaselmi_4cce10.web.dto.VehiculeRequestDTO;
import tn.esprit.raniaselmi_4cce10.web.dto.VehiculeResponseDTO;
import tn.esprit.raniaselmi_4cce10.web.dto.VehiculeResumeDTO;

public interface IVehiculeService {

    VehiculeResponseDTO create(VehiculeRequestDTO dto);

    VehiculeResponseDTO findById(Long id);

    List<VehiculeResumeDTO> findAll();

    VehiculeResponseDTO update(Long id, Vehicule vehicule);

    void deleteById(Long id);

    VehiculeResponseDTO assignAgence(Long idVehicule, Long idAgence);

    VehiculeResponseDTO addAndAssignAgence(Vehicule vehicule, Long idAgence);

    VehiculeResponseDTO assignEquipement(Long idVehicule, Long idEquipement);

    void unassignEquipement(Long idVehicule, Long idEquipement);

    // ---- Étape 6 : recherches par Keywords ----
    List<VehiculeResumeDTO> findByMarque(String marque);                                          // K1

    List<VehiculeResumeDTO> findByCategorieAndStatut(CategorieVehicule categorie, StatutVehicule statut); // K2

    List<VehiculeResumeDTO> findByTarifBetween(BigDecimal min, BigDecimal max);                   // K3

    List<VehiculeResumeDTO> findByModeleContenant(String motCle);                                 // K4

    List<VehiculeResumeDTO> findByCategories(List<CategorieVehicule> categories);                 // K5

    boolean existsByImmatriculation(String immatriculation);                                      // K6

    long countByStatut(StatutVehicule statut);                                                    // K7

    List<VehiculeResumeDTO> findTop3MoinsChers();                                                 // K8

    List<VehiculeResumeDTO> findByAgence(Long agenceId);                                          // K9

    List<VehiculeResumeDTO> findSansAgence();                                                     // K10
}
