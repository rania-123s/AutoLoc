package tn.esprit.raniaselmi_4cce10.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.raniaselmi_4cce10.domain.Client;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
