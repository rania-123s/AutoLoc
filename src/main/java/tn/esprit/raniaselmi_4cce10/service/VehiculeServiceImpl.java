package tn.esprit.raniaselmi_4cce10.service;

import java.math.BigDecimal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.raniaselmi_4cce10.domain.Agence;
import tn.esprit.raniaselmi_4cce10.domain.CategorieVehicule;
import tn.esprit.raniaselmi_4cce10.domain.Equipement;
import tn.esprit.raniaselmi_4cce10.domain.StatutVehicule;
import tn.esprit.raniaselmi_4cce10.domain.Vehicule;
import tn.esprit.raniaselmi_4cce10.exception.ResourceNotFoundException;
import tn.esprit.raniaselmi_4cce10.repository.IAgenceRepository;
import tn.esprit.raniaselmi_4cce10.repository.IEquipementRepository;
import tn.esprit.raniaselmi_4cce10.repository.IVehiculeRepository;
import tn.esprit.raniaselmi_4cce10.web.dto.VehiculeRequestDTO;
import tn.esprit.raniaselmi_4cce10.web.dto.VehiculeResponseDTO;
import tn.esprit.raniaselmi_4cce10.web.dto.VehiculeResumeDTO;
import tn.esprit.raniaselmi_4cce10.web.mapper.VehiculeMapper;

@Service
@RequiredArgsConstructor
public class VehiculeServiceImpl implements IVehiculeService {

    private final IVehiculeRepository vehiculeRepository;
    private final IAgenceRepository agenceRepository;
    private final IEquipementRepository equipementRepository;
    private final VehiculeMapper vehiculeMapper;

    // ======================= Étape 4 : CRUD avec DTO =======================

    @Override
    @Transactional(readOnly = true)
    public VehiculeResponseDTO findById(Long id) {
        // charger le véhicule (404 si absent) puis le convertir DANS la transaction
        return vehiculeMapper.toDto(getVehicule(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<VehiculeResumeDTO> findAll() {
        return vehiculeMapper.toResumeDtoList(vehiculeRepository.findAll());
    }

    @Override
    @Transactional
    public VehiculeResponseDTO create(VehiculeRequestDTO dto) {
        // K6 : une immatriculation déjà utilisée lève une exception métier (400)
        if (vehiculeRepository.existsByImmatriculation(dto.immatriculation())) {
            throw new IllegalArgumentException(
                    "L'immatriculation " + dto.immatriculation() + " est déjà utilisée");
        }
        // 1 : convertir le DTO en entité (statut = DISPONIBLE)
        Vehicule vehicule = vehiculeMapper.toEntity(dto);
        // 2 : charger l'agence à partir de dto.agenceId() et l'associer des deux côtés
        Agence agence = getAgence(dto.agenceId());
        lierAgence(vehicule, agence);
        // 3 : enregistrer puis renvoyer le DTO de réponse
        return vehiculeMapper.toDto(vehiculeRepository.save(vehicule));
    }

    @Override
    @Transactional
    public VehiculeResponseDTO update(Long id, Vehicule vehicule) {
        Vehicule existant = getVehicule(id);
        valider(vehicule);
        existant.setImmatriculation(vehicule.getImmatriculation());
        existant.setMarque(vehicule.getMarque());
        existant.setModele(vehicule.getModele());
        existant.setCategorie(vehicule.getCategorie());
        existant.setTarifJournalier(vehicule.getTarifJournalier());
        existant.setStatut(vehicule.getStatut());
        existant.setAgence(vehicule.getAgence());
        existant.setEquipements(vehicule.getEquipements());
        return vehiculeMapper.toDto(vehiculeRepository.save(existant));
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        if (!vehiculeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Vehicule", id);
        }
        vehiculeRepository.deleteById(id);
    }

    @Override
    @Transactional
    public VehiculeResponseDTO assignAgence(Long idVehicule, Long idAgence) {
        Vehicule vehicule = getVehicule(idVehicule);
        lierAgence(vehicule, getAgence(idAgence));
        return vehiculeMapper.toDto(vehiculeRepository.save(vehicule));
    }

    @Override
    @Transactional
    public VehiculeResponseDTO addAndAssignAgence(Vehicule vehicule, Long idAgence) {
        if (vehicule.getIdVehicule() != null) {
            throw new IllegalArgumentException(
                    "Un nouveau vehicule ne doit pas avoir d'identifiant");
        }
        valider(vehicule);
        lierAgence(vehicule, getAgence(idAgence));
        // les equipements du vehicule sont enregistres en cascade (CascadeType.PERSIST)
        return vehiculeMapper.toDto(vehiculeRepository.save(vehicule));
    }

    @Override
    @Transactional
    public VehiculeResponseDTO assignEquipement(Long idVehicule, Long idEquipement) {
        Vehicule vehicule = getVehicule(idVehicule);
        Equipement equipement = equipementRepository.findById(idEquipement)
                .orElseThrow(() -> new ResourceNotFoundException("Equipement", idEquipement));
        vehicule.getEquipements().add(equipement);
        return vehiculeMapper.toDto(vehiculeRepository.save(vehicule));
    }

    @Override
    @Transactional
    public void unassignEquipement(Long idVehicule, Long idEquipement) {
        Vehicule vehicule = getVehicule(idVehicule);
        if (!equipementRepository.existsById(idEquipement)) {
            throw new ResourceNotFoundException("Equipement", idEquipement);
        }
        vehicule.getEquipements()
                .removeIf(e -> idEquipement.equals(e.getIdEquipement()));
        vehiculeRepository.save(vehicule);
    }

    // ======================= Étape 6 : Keywords =======================

    @Override
    @Transactional(readOnly = true)
    public List<VehiculeResumeDTO> findByMarque(String marque) {
        return vehiculeMapper.toResumeDtoList(vehiculeRepository.findByMarque(marque));
    }

    @Override
    @Transactional(readOnly = true)
    public List<VehiculeResumeDTO> findByCategorieAndStatut(CategorieVehicule categorie, StatutVehicule statut) {
        return vehiculeMapper.toResumeDtoList(vehiculeRepository.findByCategorieAndStatut(categorie, statut));
    }

    @Override
    @Transactional(readOnly = true)
    public List<VehiculeResumeDTO> findByTarifBetween(BigDecimal min, BigDecimal max) {
        return vehiculeMapper.toResumeDtoList(
                vehiculeRepository.findByTarifJournalierBetweenOrderByTarifJournalierAsc(min, max));
    }

    @Override
    @Transactional(readOnly = true)
    public List<VehiculeResumeDTO> findByModeleContenant(String motCle) {
        return vehiculeMapper.toResumeDtoList(vehiculeRepository.findByModeleContainingIgnoreCase(motCle));
    }

    @Override
    @Transactional(readOnly = true)
    public List<VehiculeResumeDTO> findByCategories(List<CategorieVehicule> categories) {
        return vehiculeMapper.toResumeDtoList(vehiculeRepository.findByCategorieIn(categories));
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByImmatriculation(String immatriculation) {
        return vehiculeRepository.existsByImmatriculation(immatriculation);
    }

    @Override
    @Transactional(readOnly = true)
    public long countByStatut(StatutVehicule statut) {
        return vehiculeRepository.countByStatut(statut);
    }

    @Override
    @Transactional(readOnly = true)
    public List<VehiculeResumeDTO> findTop3MoinsChers() {
        return vehiculeMapper.toResumeDtoList(vehiculeRepository.findTop3ByOrderByTarifJournalierAsc());
    }

    @Override
    @Transactional(readOnly = true)
    public List<VehiculeResumeDTO> findByAgence(Long agenceId) {
        return vehiculeMapper.toResumeDtoList(vehiculeRepository.findByAgenceIdAgence(agenceId));
    }

    @Override
    @Transactional(readOnly = true)
    public List<VehiculeResumeDTO> findSansAgence() {
        return vehiculeMapper.toResumeDtoList(vehiculeRepository.findByAgenceIsNull());
    }

    // ======================= Méthodes privées =======================

    private Vehicule getVehicule(Long id) {
        return vehiculeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicule", id));
    }

    private Agence getAgence(Long idAgence) {
        return agenceRepository.findById(idAgence)
                .orElseThrow(() -> new ResourceNotFoundException("Agence", idAgence));
    }

    // synchronisation des deux côtés de l'association Vehicule <-> Agence
    private static void lierAgence(Vehicule vehicule, Agence agence) {
        vehicule.setAgence(agence);
        if (!agence.getVehicules().contains(vehicule)) {
            agence.getVehicules().add(vehicule);
        }
    }

    private static void valider(Vehicule e) {
        requireText(e.getImmatriculation(), "L'immatriculation est obligatoire");
        if (e.getTarifJournalier() != null && e.getTarifJournalier().signum() < 0) {
            throw new IllegalArgumentException("Le tarif journalier ne peut pas être négatif");
        }
    }

    private static void requireText(String valeur, String message) {
        if (valeur == null || valeur.isBlank()) {
            throw new IllegalArgumentException(message);
        }
    }
}
