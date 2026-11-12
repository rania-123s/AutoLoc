package tn.esprit.raniaselmi_4cce10.service;

import java.util.List;
import tn.esprit.raniaselmi_4cce10.domain.Vehicule;

public interface IVehiculeService {

    Vehicule create(Vehicule vehicule);

    Vehicule findById(Long id);

    List<Vehicule> findAll();

    Vehicule update(Long id, Vehicule vehicule);

    void deleteById(Long id);

    Vehicule assignAgence(Long idVehicule, Long idAgence);

    Vehicule addAndAssignAgence(Vehicule vehicule, Long idAgence);

    Vehicule assignEquipement(Long idVehicule, Long idEquipement);

    void unassignEquipement(Long idVehicule, Long idEquipement);
}
