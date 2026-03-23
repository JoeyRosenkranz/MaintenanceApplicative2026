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
}
