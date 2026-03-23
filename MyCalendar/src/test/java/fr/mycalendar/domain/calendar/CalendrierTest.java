package fr.mycalendar.domain.calendar;

import fr.mycalendar.domain.event.Evenement;
import org.junit.jupiter.api.Test;
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
}
