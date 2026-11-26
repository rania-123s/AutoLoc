package tn.esprit.raniaselmi_4cce10.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.math.BigDecimal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.raniaselmi_4cce10.domain.CategorieVehicule;
import tn.esprit.raniaselmi_4cce10.domain.StatutVehicule;
import tn.esprit.raniaselmi_4cce10.domain.Vehicule;
import tn.esprit.raniaselmi_4cce10.service.IVehiculeService;
import tn.esprit.raniaselmi_4cce10.web.dto.VehiculeRequestDTO;
import tn.esprit.raniaselmi_4cce10.web.dto.VehiculeResponseDTO;
import tn.esprit.raniaselmi_4cce10.web.dto.VehiculeResumeDTO;

@RestController
@RequestMapping("/vehicules")
@RequiredArgsConstructor
@Tag(name = "Véhicules", description = "Gestion du parc de véhicules")
public class VehiculeRestController {

    private final IVehiculeService vehiculeService;

    // ======================= Étapes 4 et 5 =======================

    @Operation(summary = "Créer un vehicule")
    @ApiResponse(responseCode = "201", description = "Vehicule créé")
    @ApiResponse(responseCode = "400", description = "Données invalides")
    @ApiResponse(responseCode = "404", description = "Agence introuvable")
    @PostMapping
    public ResponseEntity<VehiculeResponseDTO> create(@Valid @RequestBody VehiculeRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(vehiculeService.create(dto));
    }

    @Operation(summary = "Consulter un vehicule")
    @ApiResponse(responseCode = "200", description = "Vehicule trouvé")
    @ApiResponse(responseCode = "404", description = "Vehicule introuvable")
    @GetMapping("/{id}")
    public VehiculeResponseDTO findById(@Parameter(description = "Identifiant") @PathVariable Long id) {
        return vehiculeService.findById(id);
    }

    @Operation(summary = "Lister les vehicules (format résumé)")
    @ApiResponse(responseCode = "200", description = "Liste des vehicules")
    @GetMapping
    public List<VehiculeResumeDTO> findAll() {
        return vehiculeService.findAll();
    }

    @Operation(summary = "Modifier un vehicule")
    @ApiResponse(responseCode = "200", description = "Vehicule modifié")
    @ApiResponse(responseCode = "400", description = "Données invalides")
    @ApiResponse(responseCode = "404", description = "Vehicule introuvable")
    @PutMapping("/{id}")
    public VehiculeResponseDTO update(@Parameter(description = "Identifiant") @PathVariable Long id,
            @RequestBody Vehicule vehicule) {
        return vehiculeService.update(id, vehicule);
    }

    @Operation(summary = "Supprimer un vehicule")
    @ApiResponse(responseCode = "204", description = "Vehicule supprimé")
    @ApiResponse(responseCode = "404", description = "Vehicule introuvable")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@Parameter(description = "Identifiant") @PathVariable Long id) {
        vehiculeService.deleteById(id);
    }

    @Operation(summary = "Affecter un vehicule à une agence")
    @ApiResponse(responseCode = "200", description = "Vehicule affecté à l'agence")
    @ApiResponse(responseCode = "404", description = "Vehicule ou agence introuvable")
    @PutMapping("/{idVehicule}/agence/{idAgence}")
    public VehiculeResponseDTO assignAgence(
            @Parameter(description = "Identifiant du vehicule") @PathVariable Long idVehicule,
            @Parameter(description = "Identifiant de l'agence") @PathVariable Long idAgence) {
        return vehiculeService.assignAgence(idVehicule, idAgence);
    }

    @Operation(summary = "Créer un vehicule avec ses equipements et l'affecter à une agence")
    @ApiResponse(responseCode = "201", description = "Vehicule créé et affecté à l'agence")
    @ApiResponse(responseCode = "400", description = "Données invalides")
    @ApiResponse(responseCode = "404", description = "Agence introuvable")
    @PostMapping("/agence/{idAgence}")
    @ResponseStatus(HttpStatus.CREATED)
    public VehiculeResponseDTO addAndAssignAgence(@RequestBody Vehicule vehicule,
            @Parameter(description = "Identifiant de l'agence") @PathVariable Long idAgence) {
        return vehiculeService.addAndAssignAgence(vehicule, idAgence);
    }

    @Operation(summary = "Affecter un equipement à un vehicule")
    @ApiResponse(responseCode = "200", description = "Equipement affecté au vehicule")
    @ApiResponse(responseCode = "404", description = "Vehicule ou equipement introuvable")
    @PutMapping("/{idVehicule}/equipements/{idEquipement}")
    public VehiculeResponseDTO assignEquipement(
            @Parameter(description = "Identifiant du vehicule") @PathVariable Long idVehicule,
            @Parameter(description = "Identifiant de l'equipement") @PathVariable Long idEquipement) {
        return vehiculeService.assignEquipement(idVehicule, idEquipement);
    }

    @Operation(summary = "Retirer un equipement d'un vehicule")
    @ApiResponse(responseCode = "204", description = "Equipement retiré du vehicule")
    @ApiResponse(responseCode = "404", description = "Vehicule ou equipement introuvable")
    @DeleteMapping("/{idVehicule}/equipements/{idEquipement}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void unassignEquipement(
            @Parameter(description = "Identifiant du vehicule") @PathVariable Long idVehicule,
            @Parameter(description = "Identifiant de l'equipement") @PathVariable Long idEquipement) {
        vehiculeService.unassignEquipement(idVehicule, idEquipement);
    }

    // ======================= Étape 6 : recherches par Keywords =======================

    @Operation(summary = "K1 — Véhicules d'une marque")
    @GetMapping("/recherche/marque/{marque}")
    public List<VehiculeResumeDTO> findByMarque(@PathVariable String marque) {
        return vehiculeService.findByMarque(marque);
    }

    @Operation(summary = "K2 — Véhicules d'une catégorie et d'un statut")
    @GetMapping("/recherche/categorie/{categorie}/statut/{statut}")
    public List<VehiculeResumeDTO> findByCategorieAndStatut(@PathVariable CategorieVehicule categorie,
            @PathVariable StatutVehicule statut) {
        return vehiculeService.findByCategorieAndStatut(categorie, statut);
    }

    @Operation(summary = "K3 — Véhicules dont le tarif est entre min et max (tri croissant)")
    @GetMapping("/recherche/tarif/{min}/{max}")
    public List<VehiculeResumeDTO> findByTarifBetween(@PathVariable BigDecimal min,
            @PathVariable BigDecimal max) {
        return vehiculeService.findByTarifBetween(min, max);
    }

    @Operation(summary = "K4 — Véhicules dont le modèle contient un mot-clé (sans casse)")
    @GetMapping("/recherche/modele/{motCle}")
    public List<VehiculeResumeDTO> findByModeleContenant(@PathVariable String motCle) {
        return vehiculeService.findByModeleContenant(motCle);
    }

    @Operation(summary = "K5 — Véhicules appartenant à une liste de catégories (ex. SUV,BERLINE)")
    @GetMapping("/recherche/categories/{categories}")
    public List<VehiculeResumeDTO> findByCategories(@PathVariable List<CategorieVehicule> categories) {
        return vehiculeService.findByCategories(categories);
    }

    @Operation(summary = "K6 — Une immatriculation existe-t-elle déjà ?")
    @GetMapping("/recherche/immatriculation/{immatriculation}/existe")
    public boolean existsByImmatriculation(@PathVariable String immatriculation) {
        return vehiculeService.existsByImmatriculation(immatriculation);
    }

    @Operation(summary = "K7 — Nombre de véhicules dans un statut")
    @GetMapping("/recherche/statut/{statut}/nombre")
    public long countByStatut(@PathVariable StatutVehicule statut) {
        return vehiculeService.countByStatut(statut);
    }

    @Operation(summary = "K8 — Les 3 véhicules les moins chers")
    @GetMapping("/recherche/moins-chers")
    public List<VehiculeResumeDTO> findTop3MoinsChers() {
        return vehiculeService.findTop3MoinsChers();
    }

    @Operation(summary = "K9 — Véhicules d'une agence")
    @GetMapping("/recherche/agence/{agenceId}")
    public List<VehiculeResumeDTO> findByAgence(@PathVariable Long agenceId) {
        return vehiculeService.findByAgence(agenceId);
    }

    @Operation(summary = "K10 — Véhicules sans agence")
    @GetMapping("/recherche/sans-agence")
    public List<VehiculeResumeDTO> findSansAgence() {
        return vehiculeService.findSansAgence();
    }
}
