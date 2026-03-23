package fr.mycalendar.domain.vo;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

class PeriodeTest {
    @Test
    void instanciePeriode() {
        assertNotNull(new Periode(LocalDateTime.MIN, LocalDateTime.MAX));
    }
}
