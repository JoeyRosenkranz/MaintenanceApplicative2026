package fr.mycalendar.domain.event;

import fr.mycalendar.domain.vo.*;
import org.junit.jupiter.api.Test;
import java.time.Duration;
import java.time.LocalDate;
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
}
