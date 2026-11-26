package tn.esprit.raniaselmi_4cce10.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tn.esprit.raniaselmi_4cce10.domain.Reservation;

import java.util.List;
import java.time.LocalDate;

public interface IReservationRepository extends JpaRepository<Reservation, Long> {

    @Query("SELECT r FROM Reservation r WHERE r.client.email = :email")
    List<Reservation> findByClientEmail(@Param("email") String email);

    @Query("SELECT r FROM Reservation r WHERE r.dateDebut BETWEEN :d1 AND :d2")
    List<Reservation> findByDateDebutBetween(@Param("d1") LocalDate d1, @Param("d2") LocalDate d2);
}
