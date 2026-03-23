package fr.mycalendar.domain.vo;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EventIdTest {
    @Test
    void instancieEventId() {
        assertNotNull(EventId.nouveau());
    }
}
