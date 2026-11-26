package tn.esprit.raniaselmi_4cce10.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.raniaselmi_4cce10.domain.Agence;

import java.util.List;

public interface IAgenceRepository extends JpaRepository<Agence, Long> {

    Agence findByNom(String nom);

    List<Agence> findByVilleContainingIgnoreCaseAndAdresse(String ville, String adresse);
}
