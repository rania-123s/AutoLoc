package tn.esprit.raniaselmi_4cce10.service;

import java.util.List;
import java.time.LocalDate;
import tn.esprit.raniaselmi_4cce10.domain.Client;

public interface IClientService {

    Client create(Client client);

    Client findById(Long id);

    List<Client> findAll();

    Client update(Long id, Client client);

    void deleteById(Long id);

    List<Client> getByDateDebutReservationBetween(LocalDate d1, LocalDate d2);
}
