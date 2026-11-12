package tn.esprit.raniaselmi_4cce10.service.tarification.promo;

import org.springframework.stereotype.Component;

@Component
public class EtePromo implements IPromoStrategy {

    @Override
    public String code() {
        return "ETE10";
    }

    @Override
    public double appliquer(double montant) {
        return montant * 0.90;
    }
}
