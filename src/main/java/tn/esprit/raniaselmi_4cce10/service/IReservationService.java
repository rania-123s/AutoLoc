package tn.esprit.raniaselmi_4cce10.service;

import java.util.List;
import java.time.LocalDate;
import tn.esprit.raniaselmi_4cce10.domain.Reservation;

public interface IReservationService {

    Reservation create(Reservation reservation);

    Reservation findById(Long id);

    List<Reservation> findAll();

    Reservation update(Long id, Reservation reservation);

    void deleteById(Long id);

    List<Reservation> getByClientEmail(String email);

    List<Reservation> getByDateDebutBetween(LocalDate d1, LocalDate d2);
}
