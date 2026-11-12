package tn.esprit.raniaselmi_4cce10.service.tarification;

import tn.esprit.raniaselmi_4cce10.domain.Reservation;

public interface ITarificationService {
    double calculerMontant(Reservation reservation, String codePromo);
}
