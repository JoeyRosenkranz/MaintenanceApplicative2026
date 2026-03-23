package fr.mycalendar.domain.vo;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

class DateEvenementTest {
    @Test
    void instancieDate() {
        assertNotNull(new DateEvenement(LocalDate.now()));
    }
}
