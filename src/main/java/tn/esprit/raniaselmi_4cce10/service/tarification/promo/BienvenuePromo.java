package tn.esprit.raniaselmi_4cce10.service.tarification.promo;

import org.springframework.stereotype.Component;

@Component
public class BienvenuePromo implements IPromoStrategy {

    @Override
    public String code() {
        return "BIENVENUE";
    }

    @Override
    public double appliquer(double montant) {
        return montant - 20;
    }
}
