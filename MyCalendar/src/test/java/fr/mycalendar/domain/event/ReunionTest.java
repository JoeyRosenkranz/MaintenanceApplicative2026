package fr.mycalendar.domain.event;

import fr.mycalendar.domain.vo.*;
import org.junit.jupiter.api.Test;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class ReunionTest {
    @Test
    void instancieReunion() {
        Evenement reunion = new Reunion(
                EventId.nouveau(),
                new TitreEvenement("Point hebdo"),
                new DateEvenement(LocalDate.now()),
                new HeureDebut(LocalTime.now()),
                new DureeEvenement(Duration.ofMinutes(60)),
                new DescriptionEvenement("Status"),
                new Lieu("Salle A"),
                new Participants(List.of(new Participant("Alice")))
        );
        assertNotNull(reunion);
    }

    @Test
    void estDansPeriode() {
        Evenement reunion = new Reunion(
                EventId.nouveau(),
                new TitreEvenement("Point hebdo"),
                new DateEvenement(LocalDate.of(2023, 1, 1)),
                new HeureDebut(LocalTime.of(14, 0)),
                new DureeEvenement(Duration.ofMinutes(60)),
                new DescriptionEvenement("Status"),
                new Lieu("Salle A"),
                new Participants(List.of(new Participant("Alice")))
        );
        Periode p = new Periode(LocalDateTime.of(2023, 1, 1, 13, 0), LocalDateTime.of(2023, 1, 1, 15, 0));
        assertTrue(((Reunion) reunion).estDansPeriode(p));
    }
}
