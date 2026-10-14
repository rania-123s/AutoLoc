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
import tn.esprit.raniaselmi_4cce10.domain.Reservation;
import tn.esprit.raniaselmi_4cce10.service.IReservationService;

@RestController
@RequestMapping("/reservations")
@RequiredArgsConstructor
@Tag(name = "Réservations", description = "Gestion des réservations")
public class ReservationRestController {

    private final IReservationService reservationService;

    @Operation(summary = "Créer une reservation")
    @ApiResponse(responseCode = "201", description = "Reservation créée")
    @ApiResponse(responseCode = "400", description = "Données invalides")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Reservation create(@RequestBody Reservation reservation) {
        return reservationService.create(reservation);
    }

    @Operation(summary = "Consulter une reservation")
    @ApiResponse(responseCode = "200", description = "Reservation trouvée")
    @ApiResponse(responseCode = "404", description = "Reservation introuvable")
    @GetMapping("/{id}")
    public Reservation findById(@Parameter(description = "Identifiant") @PathVariable Long id) {
        return reservationService.findById(id);
    }

    @Operation(summary = "Lister les reservations")
    @ApiResponse(responseCode = "200", description = "Liste des reservations")
    @GetMapping
    public List<Reservation> findAll() {
        return reservationService.findAll();
    }

    @Operation(summary = "Modifier une reservation")
    @ApiResponse(responseCode = "200", description = "Reservation modifiée")
    @ApiResponse(responseCode = "400", description = "Données invalides")
    @ApiResponse(responseCode = "404", description = "Reservation introuvable")
    @PutMapping("/{id}")
    public Reservation update(@Parameter(description = "Identifiant") @PathVariable Long id,
            @RequestBody Reservation reservation) {
        return reservationService.update(id, reservation);
    }

    @Operation(summary = "Supprimer une reservation")
    @ApiResponse(responseCode = "204", description = "Reservation supprimée")
    @ApiResponse(responseCode = "404", description = "Reservation introuvable")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@Parameter(description = "Identifiant") @PathVariable Long id) {
        reservationService.deleteById(id);
    }
}
