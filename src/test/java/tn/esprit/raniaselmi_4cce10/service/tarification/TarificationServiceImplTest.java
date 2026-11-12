package tn.esprit.raniaselmi_4cce10.service.tarification;

import tn.esprit.raniaselmi_4cce10.domain.Reservation;
import tn.esprit.raniaselmi_4cce10.domain.Vehicule;
import tn.esprit.raniaselmi_4cce10.service.tarification.promo.*;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TarificationServiceImplTest {

    private Reservation reservation100x2() {
        Vehicule v = new Vehicule();
        v.setTarifJournalier(new BigDecimal("100"));
        Reservation r = new Reservation();
        r.setVehicule(v);
        r.setDateDebut(LocalDate.of(2026, 8, 14));
        r.setDateFin(LocalDate.of(2026, 8, 16));
        return r;
    }

    @Test
    void chaqueCodeAppliqueSaRegle() {
        var service = new TarificationServiceImpl(List.of(
                new AucunePromo(), new BienvenuePromo(), new EtePromo(), new RentreePromo()));
        Reservation r = reservation100x2();
        assertEquals(200.0, service.calculerMontant(r, null), 0.001);
        assertEquals(200.0, service.calculerMontant(r, "INCONNU"), 0.001);
        assertEquals(180.0, service.calculerMontant(r, "BIENVENUE"), 0.001);
        assertEquals(180.0, service.calculerMontant(r, "ete10"), 0.001);
        assertEquals(170.0, service.calculerMontant(r, "RENTREE"), 0.001);
    }

    @Test
    void nouveauCodeSansToucherAuService() {
        IPromoStrategy noel = new IPromoStrategy() {
            public String code() { return "NOEL"; }
            public double appliquer(double m) { return m * 0.5; }
        };
        var service = new TarificationServiceImpl(List.of(new AucunePromo(), noel));
        assertEquals(100.0, service.calculerMontant(reservation100x2(), "NOEL"), 0.001);
    }
}
