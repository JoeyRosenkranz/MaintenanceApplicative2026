package fr.mycalendar;

import fr.mycalendar.domain.calendar.Calendrier;
import fr.mycalendar.domain.event.Evenement;
import fr.mycalendar.domain.event.RendezVousPersonnel;
import fr.mycalendar.domain.vo.*;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Calendrier calendrier = new Calendrier();
        Scanner scanner = new Scanner(System.in);
        boolean quitter = false;

        System.out.println("=== MyCalendar Console TDD ===");

        while (!quitter) {
            System.out.println("\nOptions :");
            System.out.println("1 - Ajouter un rendez-vous personnel aujourd'hui");
            System.out.println("2 - Afficher les événements");
            System.out.println("3 - Quitter");
            System.out.print("Choix : ");
            
            String choix = scanner.nextLine();

            switch (choix) {
                case "1":
                    System.out.print("Titre : ");
                    String titre = scanner.nextLine();
                    System.out.print("Heure de début (HH:MM) : ");
                    String heureStr = scanner.nextLine();
                    System.out.print("Description : ");
                    String desc = scanner.nextLine();

                    LocalTime heure;
                    try {
                        heure = LocalTime.parse(heureStr);
                        Evenement rdv = new RendezVousPersonnel(
                                EventId.nouveau(),
                                new TitreEvenement(titre),
                                new DateEvenement(LocalDate.now()),
                                new HeureDebut(heure),
                                new DureeEvenement(Duration.ofMinutes(60)),
                                new DescriptionEvenement(desc)
                        );
                        
                        // Détection de conflits basique avec les existants
                        boolean conflit = false;
                        for(Evenement existant : calendrier.evenements()) {
                            if(calendrier.detecterConflits(rdv, existant)) {
                                System.out.println("[Erreur] Conflit détecté avec l'événement existant : " + existant.description());
                                conflit = true;
                                break;
                            }
                        }
                        
                        if(!conflit) {
                            calendrier.ajouter(rdv);
                            System.out.println("Rendez-vous ajouté !");
                        }
                    } catch (Exception e) {
                        System.out.println("Format invalide. Utilisez HH:MM.");
                    }
                    break;

                case "2":
                    System.out.println("\n--- Vos Événements ---");
                    if (calendrier.evenements().isEmpty()) {
                        System.out.println("Aucun événement.");
                    } else {
                        for (Evenement e : calendrier.evenements()) {
                            System.out.println("- [" + e.periode().getDebut().toLocalTime() + " à " + e.periode().getFin().toLocalTime() + "] " + e.description());
                        }
                    }
                    break;

                case "3":
                    quitter = true;
                    System.out.println("Au revoir !");
                    break;

                default:
                    System.out.println("Choix invalide.");
            }
        }
        scanner.close();
    }
}
