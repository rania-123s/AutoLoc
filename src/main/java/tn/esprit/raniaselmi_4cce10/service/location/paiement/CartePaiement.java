package tn.esprit.raniaselmi_4cce10.service.location.paiement;

import org.springframework.stereotype.Component;

import tn.esprit.raniaselmi_4cce10.domain.ModePaiement;
import tn.esprit.raniaselmi_4cce10.domain.Paiement;

@Component
public class CartePaiement implements IModePaiement {

    @Override
    public ModePaiement code() {
        return ModePaiement.CARTE;
    }

    @Override
    public double appliquerFrais(double montant) {
        return montant * 1.01; // 1 % de frais
    }

    @Override
    public void payer(Paiement paiement) {
        System.out.println("Debit de la carte bancaire...");
    }
}
