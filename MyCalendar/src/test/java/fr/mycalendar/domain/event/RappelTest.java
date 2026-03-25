package fr.mycalendar.domain.event;

import fr.mycalendar.domain.vo.*;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import static org.junit.jupiter.api.Assertions.*;

class RappelTest {
    @Test
    void instancieRappel() {
        Evenement rappel = new Rappel(
                EventId.nouveau(),
                new TitreEvenement("Acheter du pain"),
                new DateEvenement(LocalDate.now()),
                new HeureDebut(LocalTime.now()),
                new DescriptionEvenement("Boulangerie")
        );
        assertNotNull(rappel);
    }

    @Test
    void estDansPeriode() {
        Evenement rappel = new Rappel(
                EventId.nouveau(),
                new TitreEvenement("Acheter du pain"),
                new DateEvenement(LocalDate.of(2023, 1, 1)),
                new HeureDebut(LocalTime.of(10, 0)),
                new DescriptionEvenement("Boulangerie")
        );
        Periode p1 = new Periode(LocalDateTime.of(2023, 1, 1, 9, 0), LocalDateTime.of(2023, 1, 1, 11, 0));
        assertTrue(rappel.estDansPeriode(p1));
        
        Periode p2 = new Periode(LocalDateTime.of(2023, 1, 1, 11, 0), LocalDateTime.of(2023, 1, 1, 12, 0));
        assertFalse(rappel.estDansPeriode(p2));
    }

    @Test
    void description() {
        Evenement rappel = new Rappel(
                EventId.nouveau(),
                new TitreEvenement("Appeler maman"),
                new DateEvenement(LocalDate.of(2023, 1, 1)),
                new HeureDebut(LocalTime.of(18, 0)),
                new DescriptionEvenement("Souhaiter bonne annee")
        );
        assertEquals("Souhaiter bonne annee", rappel.description().valeur());
    }
}
