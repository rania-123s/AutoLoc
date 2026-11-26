package tn.esprit.raniaselmi_4cce10.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tn.esprit.raniaselmi_4cce10.domain.Client;

import java.time.LocalDate;
import java.util.List;

public interface IClientRepository extends JpaRepository<Client, Long> {

    // clients ayant au moins une réservation qui commence entre debut et fin
    @Query("select client from Client client " +
            "join client.reservations res " +
            "where res.dateDebut between :debut and :fin")
    List<Client> findByReservations(@Param("debut") LocalDate debut, @Param("fin") LocalDate fin);
}
