package fr.mycalendar.ui;

import fr.mycalendar.domain.calendar.Calendrier;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CalendarWindowTest {
    @Test
    void peutInstancierLaFenetre() {
        Calendrier calendrier = new Calendrier();
        CalendarWindow window = new CalendarWindow(calendrier);
        assertNotNull(window);
    }
}
