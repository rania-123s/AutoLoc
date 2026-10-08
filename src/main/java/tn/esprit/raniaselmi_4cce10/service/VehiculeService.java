package tn.esprit.raniaselmi_4cce10.service;

import tn.esprit.raniaselmi_4cce10.domain.Vehicule;

import java.util.List;

public interface VehiculeService {

    List<Vehicule> findAll();

    Vehicule findById(Long id);

    Vehicule save(Vehicule vehicule);

    Vehicule update(Long id, Vehicule vehicule);

    void deleteById(Long id);
}
