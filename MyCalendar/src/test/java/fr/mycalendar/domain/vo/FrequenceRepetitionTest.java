package fr.mycalendar.domain.vo;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FrequenceRepetitionTest {
    @Test
    void instancieFrequence() {
        assertNotNull(FrequenceRepetition.HEBDOMADAIRE);
        assertNotNull(FrequenceRepetition.QUOTIDIENNE);
        assertNotNull(FrequenceRepetition.MENSUELLE);
    }
}
