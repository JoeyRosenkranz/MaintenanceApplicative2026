package fr.mycalendar;

import fr.mycalendar.domain.calendar.Calendrier;
import fr.mycalendar.domain.calendar.EvenementEnConflitException;
import fr.mycalendar.domain.event.Evenement;
import fr.mycalendar.domain.event.Rappel;
import fr.mycalendar.domain.event.RendezVousPersonnel;
import fr.mycalendar.domain.vo.*;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import javax.swing.SwingUtilities;
import java.util.Scanner;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Calendrier calendrier = new Calendrier();
        Scanner scanner = new Scanner(System.in);
        boolean quitter = false;

        System.out.println("=== MyCalendar Console TDD ===");

        while (!quitter) {
            System.out.println("\nOptions :");
            System.out.println("1 - Ajouter un rendez-vous personnel aujourd'hui");
            System.out.println("2 - Ajouter un appel/rappel aujourd'hui (Nouveau type, Fct 1)");
            System.out.println("3 - Afficher tous les événements (Fct 4: descriptions polymorphiques)");
            System.out.println("4 - Afficher les événements pour une période (Fct 2)");
            System.out.println("5 - Supprimer un événement par ID (Fct 5)");
            System.out.println("6 - Lancer l'interface graphique");
            System.out.println("7 - Quitter");
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

                    try {
                        LocalTime heure = LocalTime.parse(heureStr);
                        Evenement rdv = new RendezVousPersonnel(
                                EventId.nouveau(),
                                new TitreEvenement(titre),
                                new DateEvenement(LocalDate.now()),
                                new HeureDebut(heure),
                                new DureeEvenement(Duration.ofMinutes(60)),
                                new DescriptionEvenement(desc)
                        );
                        
                        // Fct 3: Détection automatique des conflits lors de l'ajout
                        calendrier.ajouter(rdv);
                        System.out.println("Rendez-vous ajouté ! Identifiant : " + rdv.id().valeur());
                    } catch (EvenementEnConflitException e) {
                        System.out.println("[Erreur] " + e.getMessage());
                    } catch (Exception e) {
                        System.out.println("Format invalide ou erreur. Utilisez HH:MM.");
                    }
                    break;

                case "2":
                    System.out.print("Titre du rappel : ");
                    String titreRap = scanner.nextLine();
                    System.out.print("Heure de début (HH:MM) : ");
                    String heureRapStr = scanner.nextLine();
                    System.out.print("Description du rappel : ");
                    String descRap = scanner.nextLine();

                    try {
                        LocalTime heure = LocalTime.parse(heureRapStr);
                        Evenement rappel = new Rappel(
                                EventId.nouveau(),
                                new TitreEvenement(titreRap),
                                new DateEvenement(LocalDate.now()),
                                new HeureDebut(heure),
                                new DescriptionEvenement(descRap)
                        );
                        
                        calendrier.ajouter(rappel);
                        System.out.println("Rappel ajouté ! Identifiant : " + rappel.id().valeur());
                    } catch (EvenementEnConflitException e) {
                        System.out.println("[Erreur] " + e.getMessage());
                    } catch (Exception e) {
                        System.out.println("Format invalide. Utilisez HH:MM.");
                    }
                    break;

                case "3":
                    System.out.println("\n--- Vos Événements ---");
                    afficherListeEvenements(calendrier.evenements());
                    break;

                case "4":
                    System.out.println("Période recherchée aujourd'hui.");
                    System.out.print("Heure de début (HH:MM) : ");
                    String hdStr = scanner.nextLine();
                    System.out.print("Heure de fin (HH:MM) : ");
                    String hfStr = scanner.nextLine();

                    try {
                        LocalTime hd = LocalTime.parse(hdStr);
                        LocalTime hf = LocalTime.parse(hfStr);
                        Periode periode = new Periode(
                                LocalDateTime.of(LocalDate.now(), hd),
                                LocalDateTime.of(LocalDate.now(), hf)
                        );
                        
                        List<Evenement> evts = calendrier.evenementsPour(periode);
                        System.out.println("\n--- Événements trouvés pour la période ---");
                        afficherListeEvenements(evts);
                    } catch (Exception e) {
                        System.out.println("Format invalide. Utilisez HH:MM.");
                    }
                    break;
                    
                case "5":
                    System.out.print("Saisissez l'Identifiant (UUID) de l'événement à supprimer : ");
                    String idStr = scanner.nextLine();
                    try {
                        java.util.UUID.fromString(idStr); // validation
                        EventId id = EventId.depuis(idStr);
                        calendrier.supprimer(id);
                        System.out.println("Commande de suppression envoyée (si l'ID existe, il a été supprimé).");
                    } catch (Exception e) {
                        System.out.println("Format d'ID invalide.");
                    }
                    break;

                case "6":
                    System.out.println("Lancement de l'interface graphique...");
                    SwingUtilities.invokeLater(() -> {
                        fr.mycalendar.ui.CalendarWindow window = new fr.mycalendar.ui.CalendarWindow(calendrier);
                        window.setVisible(true);
                    });
                    break;

                case "7":
                    quitter = true;
                    System.out.println("Au revoir !");
                    break;

                default:
                    System.out.println("Choix invalide.");
            }
        }
        scanner.close();
    }
    
    private static void afficherListeEvenements(List<Evenement> liste) {
        if (liste.isEmpty()) {
            System.out.println("Aucun événement.");
        } else {
            for (Evenement e : liste) {
                System.out.println("ID: " + e.id().valeur());
                System.out.println(" - [" + e.periode().getDebut().toLocalTime() + " à " + e.periode().getFin().toLocalTime() + "] " + e.description().valeur());
            }
        }
    }
}
