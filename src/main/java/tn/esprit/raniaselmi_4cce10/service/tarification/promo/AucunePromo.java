package tn.esprit.raniaselmi_4cce10.service.tarification.promo;

import org.springframework.stereotype.Component;

/** Comportement par défaut : code absent ou inconnu, le montant ne change pas. */
@Component
public class AucunePromo implements IPromoStrategy {

    @Override
    public String code() {
        return "";
    }

    @Override
    public double appliquer(double montant) {
        return montant;
    }
}
