package tn.esprit.raniaselmi_4cce10.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.esprit.raniaselmi_4cce10.domain.Contrat;
import tn.esprit.raniaselmi_4cce10.domain.ModePaiement;
import tn.esprit.raniaselmi_4cce10.domain.Paiement;
import tn.esprit.raniaselmi_4cce10.domain.Reservation;
import tn.esprit.raniaselmi_4cce10.repository.IContratRepository;
import tn.esprit.raniaselmi_4cce10.repository.IReservationRepository;
import tn.esprit.raniaselmi_4cce10.service.location.legacy.SmsGateway;
import tn.esprit.raniaselmi_4cce10.service.location.legacy.SmtpClient;
import tn.esprit.raniaselmi_4cce10.service.location.paiement.IModePaiement;

/**
 * Confirmation des reservations.
 * Ecrit en urgence par le stagiaire.
 *
 * Atelier 6 - Equipe 2 : seul le bloc 4 (paiement) a ete modifie. Les modes de
 * paiement sont maintenant des strategies (IModePaiement) injectees par Spring :
 * ajouter un mode = ajouter une classe, sans rouvrir cette methode (principe OCP).
 */
@Service
public class LocationManager {

    @Autowired private IReservationRepository reservationRepository;
    @Autowired private IContratRepository contratRepository;

    private final Map<ModePaiement, IModePaiement> modesPaiement;

    @Autowired
    public LocationManager(List<IModePaiement> strategies) {
        this.modesPaiement = strategies.stream()
                .collect(Collectors.toMap(IModePaiement::code, Function.identity()));
    }

    public Contrat confirmerReservation(Long reservationId, String modePaiement,
                                        String codePromo, String canal) {

        Reservation r = reservationRepository.findById(reservationId).orElse(null);
        if (r == null) throw new RuntimeException("Reservation introuvable");

        // ---------- 1. disponibilite ----------
        for (Reservation x : reservationRepository.findAll()) {
            if (x.getVehicule().getIdVehicule().equals(r.getVehicule().getIdVehicule())
                    && !x.getIdReservation().equals(r.getIdReservation())
                    && !(r.getDateFin().isBefore(x.getDateDebut())
                         || r.getDateDebut().isAfter(x.getDateFin()))) {
                throw new RuntimeException("Vehicule indisponible");
            }
        }

        // ---------- 2. tarif de base ----------
        long j = ChronoUnit.DAYS.between(r.getDateDebut(), r.getDateFin()) + 1;
        double m = j * r.getVehicule().getTarifJournalier().doubleValue();

        // ---------- 3. remises ----------
        String t = r.getClient().getType();
        if (t.equals("PARTICULIER")) {
            if (j >= 7) m = m * 0.90;
        } else if (t.equals("ENTREPRISE")) {
            m = m * 0.85;
        } else if (t.equals("VIP")) {
            m = m * 0.80;
        }
        if (codePromo != null && codePromo.equals("BIENVENUE")) {
            m = m - 20;
        }

        // ---------- 4. paiement (Equipe 2 : strategie) ----------
        ModePaiement mode;
        try {
            mode = ModePaiement.valueOf(modePaiement);
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new RuntimeException("Mode de paiement inconnu : " + modePaiement);
        }
        IModePaiement strategie = modesPaiement.get(mode);
        if (strategie == null) throw new RuntimeException("Mode de paiement inconnu : " + modePaiement);

        Paiement p = new Paiement();
        p.setDatePaiement(LocalDate.now());
        m = strategie.appliquerFrais(m);
        strategie.payer(p);
        p.setModePaiement(mode);
        p.setMontant(BigDecimal.valueOf(m));

        // ---------- 5. contrat ----------
        Contrat c = new Contrat();
        c.setDateSignature(LocalDate.now());
        c.setMontantTotal(BigDecimal.valueOf(m));
        c.setValide(true);
        c.getPaiements().add(p);
        p.setContrat(c);
        Contrat res = contratRepository.save(c);

        // ---------- 6. notification ----------
        if (canal.equals("EMAIL")) {
            SmtpClient smtp = new SmtpClient("smtp.autoloc.tn", 25);
            smtp.send(r.getClient().getEmail(), "Contrat " + res.getIdContrat() + " confirme");
        } else if (canal.equals("SMS")) {
            SmsGateway sms = new SmsGateway("https://sms.autoloc.tn");
            sms.send(r.getClient().getTelephone(), "Contrat " + res.getIdContrat() + " confirme");
        }

        return res;
    }
}
