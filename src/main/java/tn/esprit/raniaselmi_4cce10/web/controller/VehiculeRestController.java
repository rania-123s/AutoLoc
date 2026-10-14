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
}
