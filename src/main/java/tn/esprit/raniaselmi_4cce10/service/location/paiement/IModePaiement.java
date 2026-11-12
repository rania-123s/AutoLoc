package tn.esprit.raniaselmi_4cce10.service.location.paiement;

import tn.esprit.raniaselmi_4cce10.domain.ModePaiement;
import tn.esprit.raniaselmi_4cce10.domain.Paiement;

/** Strategie de paiement : un mode = une classe (principe ouvert/ferme). */
public interface IModePaiement {

    ModePaiement code();

    /** Montant apres application des frais propres au mode. */
    double appliquerFrais(double montant);

    /** Action propre au mode (debit, attente, encaissement...). */
    void payer(Paiement paiement);
}
