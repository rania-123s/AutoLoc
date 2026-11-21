package tn.esprit.raniaselmi_4cce10.web.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import tn.esprit.raniaselmi_4cce10.domain.Vehicule;
import tn.esprit.raniaselmi_4cce10.web.dto.VehiculeDTO;

@Mapper(componentModel = "spring")
public interface VehiculeMapper {

    @Mapping(target = "nomAgence", source = "agence.nom") // Renommage : agence.nom -> nomAgence
    VehiculeDTO toDto(Vehicule vehicule);
    // MapStruct génère automatiquement l'implémentation !
}
