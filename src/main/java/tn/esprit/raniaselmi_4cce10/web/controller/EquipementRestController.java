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
import tn.esprit.raniaselmi_4cce10.domain.Equipement;
import tn.esprit.raniaselmi_4cce10.service.IEquipementService;

@RestController
@RequestMapping("/equipements")
@RequiredArgsConstructor
@Tag(name = "Équipements", description = "Gestion des équipements")
public class EquipementRestController {

    private final IEquipementService equipementService;

    @Operation(summary = "Créer un equipement")
    @ApiResponse(responseCode = "201", description = "Equipement créé")
    @ApiResponse(responseCode = "400", description = "Données invalides")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Equipement create(@RequestBody Equipement equipement) {
        return equipementService.create(equipement);
    }

    @Operation(summary = "Consulter un equipement")
    @ApiResponse(responseCode = "200", description = "Equipement trouvé")
    @ApiResponse(responseCode = "404", description = "Equipement introuvable")
    @GetMapping("/{id}")
    public Equipement findById(@Parameter(description = "Identifiant") @PathVariable Long id) {
        return equipementService.findById(id);
    }

    @Operation(summary = "Lister les equipements")
    @ApiResponse(responseCode = "200", description = "Liste des equipements")
    @GetMapping
    public List<Equipement> findAll() {
        return equipementService.findAll();
    }

    @Operation(summary = "Modifier un equipement")
    @ApiResponse(responseCode = "200", description = "Equipement modifié")
    @ApiResponse(responseCode = "400", description = "Données invalides")
    @ApiResponse(responseCode = "404", description = "Equipement introuvable")
    @PutMapping("/{id}")
    public Equipement update(@Parameter(description = "Identifiant") @PathVariable Long id,
            @RequestBody Equipement equipement) {
        return equipementService.update(id, equipement);
    }

    @Operation(summary = "Supprimer un equipement")
    @ApiResponse(responseCode = "204", description = "Equipement supprimé")
    @ApiResponse(responseCode = "404", description = "Equipement introuvable")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@Parameter(description = "Identifiant") @PathVariable Long id) {
        equipementService.deleteById(id);
    }
}
