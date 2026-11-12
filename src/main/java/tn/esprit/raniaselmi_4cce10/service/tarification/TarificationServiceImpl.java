package tn.esprit.raniaselmi_4cce10.service.tarification;

import tn.esprit.raniaselmi_4cce10.domain.Reservation;
import tn.esprit.raniaselmi_4cce10.service.tarification.promo.IPromoStrategy;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class TarificationServiceImpl implements ITarificationService {

    private final Map<String, IPromoStrategy> strategies;
    private final IPromoStrategy parDefaut;

    // Spring injecte TOUTES les stratégies présentes dans le contexte :
    // ajouter un code promo = ajouter un @Component, ce constructeur ne change jamais.
    public TarificationServiceImpl(List<IPromoStrategy> toutes) {
        this.strategies = toutes.stream()
                .filter(s -> !s.code().isEmpty())
                .collect(Collectors.toMap(s -> s.code().toUpperCase(), Function.identity()));
        this.parDefaut = toutes.stream()
                .filter(s -> s.code().isEmpty())
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Stratégie par défaut manquante"));
    }

    @Override
    public double calculerMontant(Reservation r, String codePromo) {
        long jours = Math.max(1, ChronoUnit.DAYS.between(r.getDateDebut(), r.getDateFin()));
        BigDecimal tarif = r.getVehicule().getTarifJournalier();
        double montant = tarif.doubleValue() * jours;

        String cle = codePromo == null ? "" : codePromo.trim().toUpperCase();
        return strategies.getOrDefault(cle, parDefaut).appliquer(montant);
    }
}
