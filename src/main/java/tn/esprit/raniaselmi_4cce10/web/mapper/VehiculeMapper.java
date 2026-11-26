package tn.esprit.raniaselmi_4cce10.web.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import tn.esprit.raniaselmi_4cce10.domain.Equipement;
import tn.esprit.raniaselmi_4cce10.domain.Vehicule;
import tn.esprit.raniaselmi_4cce10.web.dto.VehiculeRequestDTO;
import tn.esprit.raniaselmi_4cce10.web.dto.VehiculeResponseDTO;
import tn.esprit.raniaselmi_4cce10.web.dto.VehiculeResumeDTO;

@Mapper(componentModel = "spring")
public interface VehiculeMapper {

    // 1. Entité -> DTO détaillé
    @Mapping(target = "id", source = "idVehicule")          // l'id s'appelle idVehicule dans l'entité
    @Mapping(target = "nomAgence", source = "agence.nom")   // nom de l'agence
    @Mapping(target = "equipements", source = "equipements") // Set<Equipement> -> List<String> via toLibelle
    VehiculeResponseDTO toDto(Vehicule vehicule);

    // 2. Entité -> DTO résumé
    @Mapping(target = "id", source = "idVehicule")
    VehiculeResumeDTO toResumeDto(Vehicule vehicule);

    List<VehiculeResumeDTO> toResumeDtoList(List<Vehicule> vehicules);

    // 3. DTO -> Entité
    @Mapping(target = "idVehicule", ignore = true)
    @Mapping(target = "statut", constant = "DISPONIBLE")
    @Mapping(target = "agence", ignore = true)       // résolue par le service à partir de agenceId
    @Mapping(target = "equipements", ignore = true)
    // (pas de réservations ni de maintenances dans l'entité Vehicule de ce projet)
    Vehicule toEntity(VehiculeRequestDTO dto);

    // 4. Conversion d'un élément de la collection d'équipements
    default String toLibelle(Equipement equipement) {
        return equipement.getLibelle();
    }
}
