package fr.mycalendar.domain.vo;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TitreEvenementTest {
    @Test
    void instancieTitre() {
        assertNotNull(new TitreEvenement("Réunion Annuelle"));
    }
}
