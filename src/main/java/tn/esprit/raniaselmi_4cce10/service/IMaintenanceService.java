package tn.esprit.raniaselmi_4cce10.service;

import java.util.List;
import tn.esprit.raniaselmi_4cce10.domain.Maintenance;

public interface IMaintenanceService {

    Maintenance create(Maintenance maintenance);

    Maintenance findById(Long id);

    List<Maintenance> findAll();

    Maintenance update(Long id, Maintenance maintenance);

    void deleteById(Long id);
}
