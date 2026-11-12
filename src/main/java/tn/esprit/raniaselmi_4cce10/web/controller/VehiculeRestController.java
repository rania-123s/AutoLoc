package tn.esprit.raniaselmi_4cce10.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.raniaselmi_4cce10.domain.Vehicule;
import tn.esprit.raniaselmi_4cce10.service.IVehiculeService;

@RestController
@RequestMapping("/vehicules")
@RequiredArgsConstructor
@Tag(name = "Véhicules", description = "Gestion du parc de véhicules")
public class VehiculeRestController {

    private final IVehiculeService vehiculeService;

    @Operation(summary = "Créer un vehicule")
    @ApiResponse(responseCode = "201", description = "Vehicule créé")
    @ApiResponse(responseCode = "400", description = "Données invalides")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Vehicule create(@RequestBody Vehicule vehicule) {
        return vehiculeService.create(vehicule);
    }

    @Operation(summary = "Consulter un vehicule")
    @ApiResponse(responseCode = "200", description = "Vehicule trouvé")
    @ApiResponse(responseCode = "404", description = "Vehicule introuvable")
    @GetMapping("/{id}")
    public Vehicule findById(@Parameter(description = "Identifiant") @PathVariable Long id) {
        return vehiculeService.findById(id);
    }

    @Operation(summary = "Lister les vehicules")
    @ApiResponse(responseCode = "200", description = "Liste des vehicules")
    @GetMapping
    public List<Vehicule> findAll() {
        return vehiculeService.findAll();
    }

    @Operation(summary = "Modifier un vehicule")
    @ApiResponse(responseCode = "200", description = "Vehicule modifié")
    @ApiResponse(responseCode = "400", description = "Données invalides")
    @ApiResponse(responseCode = "404", description = "Vehicule introuvable")
    @PutMapping("/{id}")
    public Vehicule update(@Parameter(description = "Identifiant") @PathVariable Long id,
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
    public Vehicule assignAgence(
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
    public Vehicule addAndAssignAgence(@RequestBody Vehicule vehicule,
            @Parameter(description = "Identifiant de l'agence") @PathVariable Long idAgence) {
        return vehiculeService.addAndAssignAgence(vehicule, idAgence);
    }

    @Operation(summary = "Affecter un equipement à un vehicule")
    @ApiResponse(responseCode = "200", description = "Equipement affecté au vehicule")
    @ApiResponse(responseCode = "404", description = "Vehicule ou equipement introuvable")
    @PutMapping("/{idVehicule}/equipements/{idEquipement}")
    public Vehicule assignEquipement(
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
}
