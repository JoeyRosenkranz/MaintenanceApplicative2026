package fr.mycalendar.domain.vo;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class LieuTest {
    @Test
    void instancieLieu() {
        assertNotNull(new Lieu("Salle A"));
    }
}
