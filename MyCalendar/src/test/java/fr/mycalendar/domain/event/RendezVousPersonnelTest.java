package fr.mycalendar.domain.event;

import fr.mycalendar.domain.vo.*;
import org.junit.jupiter.api.Test;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import static org.junit.jupiter.api.Assertions.*;

class RendezVousPersonnelTest {
    @Test
    void instancieRendezVousPersonnel() {
        Evenement rdv = new RendezVousPersonnel(
                EventId.nouveau(),
                new TitreEvenement("RDV Docteur"),
                new DateEvenement(LocalDate.now()),
                new HeureDebut(LocalTime.now()),
                new DureeEvenement(Duration.ofMinutes(30)),
                new DescriptionEvenement("Routine")
        );
        assertNotNull(rdv);
    }

    @Test
    void estDansPeriode() {
        Evenement rdv = new RendezVousPersonnel(
                EventId.nouveau(),
                new TitreEvenement("RDV Docteur"),
                new DateEvenement(LocalDate.of(2023, 1, 1)),
                new HeureDebut(LocalTime.of(10, 0)),
                new DureeEvenement(Duration.ofMinutes(30)),
                new DescriptionEvenement("Routine")
        );
        Periode p = new Periode(LocalDateTime.of(2023, 1, 1, 9, 0), LocalDateTime.of(2023, 1, 1, 11, 0));
        assertTrue(((RendezVousPersonnel) rdv).estDansPeriode(p));
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
        assertEquals("Gym", rdv.description());
    }
}
