package tn.esprit.raniaselmi_4cce10.service.location.paiement;

import org.springframework.stereotype.Component;

import tn.esprit.raniaselmi_4cce10.domain.ModePaiement;
import tn.esprit.raniaselmi_4cce10.domain.Paiement;

@Component
public class EspecesPaiement implements IModePaiement {

    @Override
    public ModePaiement code() {
        return ModePaiement.ESPECES;
    }

    @Override
    public double appliquerFrais(double montant) {
        return montant;
    }

    @Override
    public void payer(Paiement paiement) {
        System.out.println("Encaissement en especes a l'agence");
    }
}
