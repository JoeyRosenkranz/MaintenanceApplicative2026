package fr.mycalendar.domain.vo;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ParticipantTest {
    @Test
    void instancieParticipant() {
        assertNotNull(new Participant("Jean Dupont"));
    }
}
