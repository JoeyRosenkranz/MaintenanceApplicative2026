package fr.mycalendar.domain.vo;

import org.junit.jupiter.api.Test;
import java.time.Duration;
import static org.junit.jupiter.api.Assertions.*;

class DureeEvenementTest {
    @Test
    void instancieDuree() {
        assertNotNull(new DureeEvenement(Duration.ofMinutes(45)));
    }
}
