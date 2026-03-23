package fr.mycalendar;

import fr.mycalendar.domain.calendar.Calendrier;
import fr.mycalendar.domain.event.Evenement;
import fr.mycalendar.domain.event.RendezVousPersonnel;
import fr.mycalendar.domain.event.Reunion;
import fr.mycalendar.domain.vo.*;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Calendrier calendrier = new Calendrier();

        Evenement rdv = new RendezVousPersonnel(
                EventId.nouveau(),
                new TitreEvenement("Sport"),
                new DateEvenement(LocalDate.now()),
                new HeureDebut(LocalTime.of(18, 0)),
                new DureeEvenement(Duration.ofMinutes(60)),
                new DescriptionEvenement("Séance de gym")
        );

        Evenement reunion = new Reunion(
                EventId.nouveau(),
                new TitreEvenement("Point Projet"),
                new DateEvenement(LocalDate.now()),
                new HeureDebut(LocalTime.of(14, 0)),
                new DureeEvenement(Duration.ofMinutes(45)),
                new DescriptionEvenement("Avancement TDD"),
                new Lieu("Salle A"),
                new Participants(List.of(new Participant("Alice"), new Participant("Bob")))
        );

        calendrier.ajouter(rdv);
        calendrier.ajouter(reunion);

        System.out.println("Événements d'aujourd'hui :");
        for (Evenement e : calendrier.evenements()) {
            System.out.println("- " + e.description() + " de " + e.periode().getDebut() + " à " + e.periode().getFin());
        }
    }
}
