package fr.mycalendar.domain.vo;

import org.junit.jupiter.api.Test;
import java.time.LocalTime;
import static org.junit.jupiter.api.Assertions.*;

class HeureDebutTest {
    @Test
    void instancieHeure() {
        assertNotNull(new HeureDebut(LocalTime.now()));
    }
}
