package tn.esprit.raniaselmi_4cce10.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.raniaselmi_4cce10.domain.Paiement;
import tn.esprit.raniaselmi_4cce10.service.IPaiementService;

@RestController
@RequestMapping("/paiements")
@RequiredArgsConstructor
@Tag(name = "Paiements", description = "Consultation des paiements (lecture seule)")
public class PaiementRestController {

    private final IPaiementService paiementService;

    @Operation(summary = "Consulter un paiement")
    @ApiResponse(responseCode = "200", description = "Paiement trouvé")
    @ApiResponse(responseCode = "404", description = "Paiement introuvable")
    @GetMapping("/{id}")
    public Paiement findById(@Parameter(description = "Identifiant") @PathVariable Long id) {
        return paiementService.findById(id);
    }

    @Operation(summary = "Lister les paiements")
    @ApiResponse(responseCode = "200", description = "Liste des paiements")
    @GetMapping
    public List<Paiement> findAll() {
        return paiementService.findAll();
    }
}
