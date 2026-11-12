package tn.esprit.raniaselmi_4cce10.service.location.paiement;

import org.springframework.stereotype.Component;

import tn.esprit.raniaselmi_4cce10.domain.ModePaiement;
import tn.esprit.raniaselmi_4cce10.domain.Paiement;

@Component
public class VirementPaiement implements IModePaiement {

    @Override
    public ModePaiement code() {
        return ModePaiement.VIREMENT;
    }

    @Override
    public double appliquerFrais(double montant) {
        return montant;
    }

    @Override
    public void payer(Paiement paiement) {
        System.out.println("Attente du virement...");
    }
}
