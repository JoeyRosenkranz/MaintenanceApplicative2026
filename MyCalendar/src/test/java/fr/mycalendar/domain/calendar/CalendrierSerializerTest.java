package fr.mycalendar.domain.calendar;

import fr.mycalendar.domain.event.Evenement;
import fr.mycalendar.domain.event.RendezVousPersonnel;
import fr.mycalendar.domain.vo.*;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
        assertEquals("Gym", rdvImporte.description().valeur());
    }

    @Test
    void deserialiserReunion() throws Exception {
        Calendrier calendrier = new Calendrier();
        Evenement reunion = new fr.mycalendar.domain.event.Reunion(
                EventId.nouveau(),
                new TitreEvenement("Point hebdo"),
                new DateEvenement(LocalDate.of(2023, 1, 2)),
                new HeureDebut(LocalTime.of(10, 0)),
                new DureeEvenement(Duration.ofMinutes(30)),
                new DescriptionEvenement("Reunion de sync"),
                new Lieu("Salle A"),
                new Participants(java.util.List.of(new Participant("Alice"), new Participant("Bob")))
        );
        calendrier.ajouter(reunion);

        CalendrierSerializer serializer = new CalendrierSerializer();
        String json = serializer.exporter(calendrier);
        System.out.println("JSON Reunion: " + json);
        
        Calendrier importe = serializer.importer(json);
        assertTrue(importe.evenements().size() == 1);
        Evenement importeReunion = importe.evenements().get(0);
        assertTrue(importeReunion instanceof fr.mycalendar.domain.event.Reunion);
        assertEquals("Reunion de sync", importeReunion.description().valeur());
    }

    @Test
    void deserialiserEvenementPeriodique() throws Exception {
        Evenement base = new RendezVousPersonnel(
                EventId.nouveau(),
                new TitreEvenement("Sport"),
                new DateEvenement(LocalDate.of(2023, 1, 1)),
                new HeureDebut(LocalTime.of(18, 0)),
                new DureeEvenement(Duration.ofMinutes(60)),
                new DescriptionEvenement("Gym")
        );
        Evenement periodique = new fr.mycalendar.domain.event.EvenementPeriodique(base, fr.mycalendar.domain.vo.FrequenceRepetition.HEBDOMADAIRE);
        
        Calendrier calendrier = new Calendrier();
        calendrier.ajouter(periodique);

        CalendrierSerializer serializer = new CalendrierSerializer();
        String json = serializer.exporter(calendrier);
        System.out.println("JSON Periodique: " + json);

        Calendrier importe = serializer.importer(json);
        assertTrue(importe.evenements().size() == 1);
        Evenement importePeriodique = importe.evenements().get(0);
        assertTrue(importePeriodique instanceof fr.mycalendar.domain.event.EvenementPeriodique);
        assertTrue(importePeriodique.description().valeur().contains("HEBDOMADAIRE"));
    }
}
