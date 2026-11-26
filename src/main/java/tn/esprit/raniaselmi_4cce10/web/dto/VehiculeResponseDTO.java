package tn.esprit.raniaselmi_4cce10.web.dto;

import java.math.BigDecimal;
import java.util.List;
import tn.esprit.raniaselmi_4cce10.domain.CategorieVehicule;
import tn.esprit.raniaselmi_4cce10.domain.StatutVehicule;

public record VehiculeResponseDTO(
        Long id,
        String immatriculation,
        String marque,
        String modele,
        CategorieVehicule categorie,
        BigDecimal tarifJournalier,
        StatutVehicule statut,
        String nomAgence,                 // nom de l'agence
        List<String> equipements          // libellés des équipements
) { }
