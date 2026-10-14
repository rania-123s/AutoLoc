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
import tn.esprit.raniaselmi_4cce10.domain.Employe;
import tn.esprit.raniaselmi_4cce10.service.IEmployeService;

@RestController
@RequestMapping("/employes")
@RequiredArgsConstructor
@Tag(name = "Employés", description = "Gestion des employés")
public class EmployeRestController {

    private final IEmployeService employeService;

    @Operation(summary = "Créer un employe")
    @ApiResponse(responseCode = "201", description = "Employe créé")
    @ApiResponse(responseCode = "400", description = "Données invalides")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Employe create(@RequestBody Employe employe) {
        return employeService.create(employe);
    }

    @Operation(summary = "Consulter un employe")
    @ApiResponse(responseCode = "200", description = "Employe trouvé")
    @ApiResponse(responseCode = "404", description = "Employe introuvable")
    @GetMapping("/{id}")
    public Employe findById(@Parameter(description = "Identifiant") @PathVariable Long id) {
        return employeService.findById(id);
    }

    @Operation(summary = "Lister les employes")
    @ApiResponse(responseCode = "200", description = "Liste des employes")
    @GetMapping
    public List<Employe> findAll() {
        return employeService.findAll();
    }

    @Operation(summary = "Modifier un employe")
    @ApiResponse(responseCode = "200", description = "Employe modifié")
    @ApiResponse(responseCode = "400", description = "Données invalides")
    @ApiResponse(responseCode = "404", description = "Employe introuvable")
    @PutMapping("/{id}")
    public Employe update(@Parameter(description = "Identifiant") @PathVariable Long id,
            @RequestBody Employe employe) {
        return employeService.update(id, employe);
    }

    @Operation(summary = "Supprimer un employe")
    @ApiResponse(responseCode = "204", description = "Employe supprimé")
    @ApiResponse(responseCode = "404", description = "Employe introuvable")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@Parameter(description = "Identifiant") @PathVariable Long id) {
        employeService.deleteById(id);
    }
}
