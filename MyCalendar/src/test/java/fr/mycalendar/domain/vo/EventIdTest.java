package fr.mycalendar.domain.vo;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EventIdTest {
    @Test
    void instancieEventId() {
        assertNotNull(EventId.nouveau());
    }

    @Test
    void recreerDepuisChaine() {
        String uuid = java.util.UUID.randomUUID().toString();
        EventId id = EventId.depuis(uuid);
        assertEquals(uuid, id.valeur());
    }
}
