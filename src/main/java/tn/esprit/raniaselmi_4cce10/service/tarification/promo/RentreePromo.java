package tn.esprit.raniaselmi_4cce10.service.tarification.promo;

import org.springframework.stereotype.Component;

@Component
public class RentreePromo implements IPromoStrategy {

    @Override
    public String code() {
        return "RENTREE";
    }

    @Override
    public double appliquer(double montant) {
        return montant * 0.85;
    }
}
