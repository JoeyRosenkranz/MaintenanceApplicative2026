package fr.mycalendar.domain.vo;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class ParticipantsTest {
    @Test
    void instancieParticipants() {
        assertNotNull(new Participants(List.of(new Participant("Alice"))));
    }
}
