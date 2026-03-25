package fr.mycalendar.domain.event;

import fr.mycalendar.domain.vo.*;
import org.junit.jupiter.api.Test;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import static org.junit.jupiter.api.Assertions.*;

class EvenementPeriodiqueTest {
    @Test
    void instancieEvenementPeriodique() {
        Evenement base = new RendezVousPersonnel(
                EventId.nouveau(),
                new TitreEvenement("Sport"),
                new DateEvenement(LocalDate.now()),
                new HeureDebut(LocalTime.now()),
                new DureeEvenement(Duration.ofMinutes(60)),
                new DescriptionEvenement("Gym")
        );
        Evenement periodique = new EvenementPeriodique(base, FrequenceRepetition.HEBDOMADAIRE);
        assertNotNull(periodique);
        assertEquals(base.id(), periodique.id());
    }

    @Test
    void estDansPeriode() {
        Evenement rdv = new RendezVousPersonnel(
                EventId.nouveau(),
                new TitreEvenement("Sport"),
                new DateEvenement(LocalDate.of(2023, 1, 1)),
                new HeureDebut(LocalTime.of(18, 0)),
                new DureeEvenement(Duration.ofMinutes(60)),
                new DescriptionEvenement("Gym")
        );
        EvenementPeriodique ep = new EvenementPeriodique(rdv, FrequenceRepetition.QUOTIDIENNE);
        fr.mycalendar.domain.vo.Periode p = new fr.mycalendar.domain.vo.Periode(
                LocalDateTime.of(2023, 1, 1, 17, 0),
                LocalDateTime.of(2023, 1, 1, 19, 0)
        );
        assertTrue(ep.estDansPeriode(p));
    }

    @Test
    void description() {
        Evenement rdv = new RendezVousPersonnel(
                EventId.nouveau(),
                new TitreEvenement("Sport"),
                new DateEvenement(LocalDate.of(2023, 1, 1)),
                new HeureDebut(LocalTime.of(18, 0)),
                new DureeEvenement(Duration.ofMinutes(60)),
                new DescriptionEvenement("Gym")
        );
        EvenementPeriodique ep = new EvenementPeriodique(rdv, FrequenceRepetition.QUOTIDIENNE);
        assertEquals("Gym (QUOTIDIENNE)", ep.description().valeur());
    }
}
