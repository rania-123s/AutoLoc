package tn.esprit.raniaselmi_4cce10.service.tarification.promo;

/** Une règle de remise = une classe. Le service de tarification ne connaît que cette interface. */
public interface IPromoStrategy {

    /** Le code promo géré par cette stratégie (ex. "ETE10"), en majuscules. */
    String code();

    /** Applique la remise au montant et retourne le nouveau montant. */
    double appliquer(double montant);
}
