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
}
