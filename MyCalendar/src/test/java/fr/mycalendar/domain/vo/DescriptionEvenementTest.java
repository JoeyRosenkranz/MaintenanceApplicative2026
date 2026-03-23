package fr.mycalendar.domain.vo;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DescriptionEvenementTest {
    @Test
    void instancieDescription() {
        assertNotNull(new DescriptionEvenement("Une description"));
    }
}
