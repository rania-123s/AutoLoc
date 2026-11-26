package tn.esprit.raniaselmi_4cce10.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import tn.esprit.raniaselmi_4cce10.domain.CategorieVehicule;

// Pas d'id (généré par la base) ni de statut (DISPONIBLE à la création)
public record VehiculeRequestDTO(

        @NotBlank(message = "L'immatriculation est obligatoire")
        @Pattern(regexp = "^[0-9]{1,3}TU[0-9]{1,4}$",
                message = "L'immatriculation doit respecter le format 120TU1001")
        String immatriculation,

        @NotBlank(message = "La marque est obligatoire")
        @Size(max = 30, message = "La marque ne doit pas dépasser 30 caractères")
        String marque,

        @NotBlank(message = "Le modèle est obligatoire")
        @Size(max = 30, message = "Le modèle ne doit pas dépasser 30 caractères")
        String modele,

        @NotNull(message = "La catégorie est obligatoire")
        CategorieVehicule categorie,

        @NotNull(message = "Le tarif journalier est obligatoire")
        @Positive(message = "Le tarif journalier doit être strictement positif")
        BigDecimal tarifJournalier,

        @NotNull(message = "L'agence est obligatoire")
        Long agenceId) { }
