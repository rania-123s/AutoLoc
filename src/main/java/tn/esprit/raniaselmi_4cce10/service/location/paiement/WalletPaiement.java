package tn.esprit.raniaselmi_4cce10.service.location.paiement;

import org.springframework.stereotype.Component;

import tn.esprit.raniaselmi_4cce10.domain.ModePaiement;
import tn.esprit.raniaselmi_4cce10.domain.Paiement;

@Component
public class WalletPaiement implements IModePaiement {

    @Override
    public ModePaiement code() {
        return ModePaiement.WALLET;
    }

    @Override
    public double appliquerFrais(double montant) {
        return montant * 1.02; // 2 % de frais
    }

    @Override
    public void payer(Paiement paiement) {
        System.out.println("Debit du portefeuille electronique...");
    }
}
