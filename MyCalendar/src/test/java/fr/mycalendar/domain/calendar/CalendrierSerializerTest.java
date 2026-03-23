package fr.mycalendar.domain.calendar;

import fr.mycalendar.domain.event.Evenement;
import fr.mycalendar.domain.event.RendezVousPersonnel;
import fr.mycalendar.domain.vo.*;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertTrue;

class CalendrierSerializerTest {
    @Test
    void serialiserRendezVousPersonnel() throws Exception {
        CalendrierSerializer serializer = new CalendrierSerializer();
        Evenement rdv = new RendezVousPersonnel(
                EventId.nouveau(),
                new TitreEvenement("Sport"),
                new DateEvenement(LocalDate.of(2023, 1, 1)),
                new HeureDebut(LocalTime.of(18, 0)),
                new DureeEvenement(Duration.ofMinutes(60)),
                new DescriptionEvenement("Gym")
        );
        String json = serializer.exporter(rdv);
        System.out.println("JSON généré : " + json);
        
        assertTrue(json.contains("Sport"));
        assertTrue(json.contains("2023-01-01"));
        assertTrue(json.contains("18:00"));
    }

    @Test
    void serialiserCalendrier() throws Exception {
        Calendrier calendrier = new Calendrier();
        Evenement rdv = new RendezVousPersonnel(
                EventId.nouveau(),
                new TitreEvenement("Sport"),
                new DateEvenement(LocalDate.of(2023, 1, 1)),
                new HeureDebut(LocalTime.of(18, 0)),
                new DureeEvenement(Duration.ofMinutes(60)),
                new DescriptionEvenement("Gym")
        );
        calendrier.ajouter(rdv);

        CalendrierSerializer serializer = new CalendrierSerializer();
        String json = serializer.exporter(calendrier);
        System.out.println("Calendrier JSON : " + json);
        
        assertTrue(json.contains("Sport"));
    }

    @Test
    void deserialiserCalendrier() throws Exception {
        Calendrier calendrier = new Calendrier();
        Evenement rdv = new RendezVousPersonnel(
                EventId.nouveau(),
                new TitreEvenement("Sport"),
                new DateEvenement(LocalDate.of(2023, 1, 1)),
                new HeureDebut(LocalTime.of(18, 0)),
                new DureeEvenement(Duration.ofMinutes(60)),
                new DescriptionEvenement("Gym")
        );
        calendrier.ajouter(rdv);

        CalendrierSerializer serializer = new CalendrierSerializer();
        String json = serializer.exporter(calendrier);
        
        Calendrier importe = serializer.importer(json);
        assertTrue(importe.evenements().size() == 1);
        Evenement rdvImporte = importe.evenements().get(0);
        assertTrue(rdvImporte instanceof RendezVousPersonnel);
        assertTrue(rdvImporte.description().equals("Gym"));
    }
}
