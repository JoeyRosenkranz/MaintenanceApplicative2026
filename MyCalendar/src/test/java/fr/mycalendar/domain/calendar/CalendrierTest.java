package fr.mycalendar.domain.calendar;

import fr.mycalendar.domain.event.Evenement;
import fr.mycalendar.domain.event.RendezVousPersonnel;
import fr.mycalendar.domain.event.Reunion;
import fr.mycalendar.domain.vo.*;
import org.junit.jupiter.api.Test;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class CalendrierTest {
    @Test
    void instancieCalendrierVide() {
        Calendrier calendrier = new Calendrier();
        assertTrue(calendrier.evenements().isEmpty());
    }

    @Test
    void ajouterEvenement() {
        Calendrier calendrier = new Calendrier();
        Evenement e = new Reunion(fr.mycalendar.domain.vo.EventId.nouveau(), null, null, null, null, null, null, null);
        calendrier.ajouter(e);
        assertEquals(1, calendrier.evenements().size());
    }

    @Test
    void evenementsPour() {
        Calendrier calendrier = new Calendrier();
        Evenement e1 = new RendezVousPersonnel(
                EventId.nouveau(),
                new TitreEvenement("Sport"),
                new DateEvenement(LocalDate.of(2023, 1, 1)),
                new HeureDebut(LocalTime.of(10, 0)),
                new DureeEvenement(Duration.ofMinutes(60)),
                new DescriptionEvenement("Gym")
        );
        Evenement e2 = new RendezVousPersonnel(
                EventId.nouveau(),
                new TitreEvenement("Docteur"),
                new DateEvenement(LocalDate.of(2023, 1, 2)),
                new HeureDebut(LocalTime.of(14, 0)),
                new DureeEvenement(Duration.ofMinutes(30)),
                new DescriptionEvenement("Checkup")
        );
        calendrier.ajouter(e1);
        calendrier.ajouter(e2);
        
        fr.mycalendar.domain.vo.Periode p = new fr.mycalendar.domain.vo.Periode(
                LocalDateTime.of(2023, 1, 1, 9, 0),
                LocalDateTime.of(2023, 1, 1, 12, 0)
        );
        List<Evenement> evts = calendrier.evenementsPour(p);
        
        assertEquals(1, evts.size());
        assertEquals(e1.id(), evts.get(0).id());
    }
}
