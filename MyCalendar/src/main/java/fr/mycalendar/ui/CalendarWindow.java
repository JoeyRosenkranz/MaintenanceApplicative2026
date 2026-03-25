package fr.mycalendar.ui;

import fr.mycalendar.domain.calendar.Calendrier;
import fr.mycalendar.domain.calendar.EvenementEnConflitException;
import fr.mycalendar.domain.event.Evenement;
import fr.mycalendar.domain.vo.*;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Duration;
import java.util.List;

public class CalendarWindow extends JFrame {
    private final Calendrier calendrier;
    private final DefaultListModel<Evenement> listModel;
    private final JList<Evenement> eventList;
    private final JLabel statusLabel;

    public CalendarWindow(Calendrier calendrier) {
        this.calendrier = calendrier;
        this.listModel = new DefaultListModel<>();
        this.eventList = new JList<>(listModel);
        this.statusLabel = new JLabel("Prêt.");

        setTitle("MyCalendar - TDD Strict");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(750, 500);
        setLocationRelativeTo(null);

        setupComponents();
        refreshList();
    }

    private void setupComponents() {
        setLayout(new BorderLayout(8, 8));

        // Titre
        JLabel title = new JLabel("  📅 MyCalendar", SwingConstants.LEFT);
        title.setFont(new Font("SansSerif", Font.BOLD, 18));
        title.setBorder(BorderFactory.createEmptyBorder(8, 8, 4, 8));
        add(title, BorderLayout.NORTH);

        // Liste des événements avec renderer riche
        eventList.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Evenement) {
                    Evenement e = (Evenement) value;
                    String type = e.getClass().getSimpleName();
                    String debut = e.periode().getDebut().toLocalTime().toString();
                    String fin = e.periode().getFin().toLocalTime().equals(e.periode().getDebut().toLocalTime())
                            ? "" : " → " + e.periode().getFin().toLocalTime();
                    String desc = e.description().valeur();
                    String id = e.id().valeur().substring(0, 8) + "…";
                    label.setText(String.format("[%s]  %s%s  |  %s  |  ID: %s", type, debut, fin, desc, id));
                }
                label.setBorder(BorderFactory.createEmptyBorder(6, 8, 6, 8));
                return label;
            }
        });

        JScrollPane scrollPane = new JScrollPane(eventList);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Événements"));
        add(scrollPane, BorderLayout.CENTER);

        // Panneau boutons — en deux rangées
        JPanel southPanel = new JPanel(new BorderLayout());

        // Rangée 1 : Ajouter
        JPanel addPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 4));
        addPanel.setBorder(BorderFactory.createTitledBorder("Ajouter"));
        JButton btnAddRdv = new JButton("➕ Rendez-vous");
        JButton btnAddRappel = new JButton("🔔 Rappel");
        btnAddRdv.addActionListener(e -> showAddEventDialog());
        btnAddRappel.addActionListener(e -> showAddRappelDialog());
        addPanel.add(btnAddRdv);
        addPanel.add(btnAddRappel);

        // Rangée 2 : Actions
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 4));
        actionPanel.setBorder(BorderFactory.createTitledBorder("Actions"));
        JButton btnFilter = new JButton("🔍 Filtrer par période");
        JButton btnDelete = new JButton("🗑 Supprimer sélection");
        JButton btnExport = new JButton("💾 Exporter JSON");
        JButton btnImport = new JButton("📂 Importer JSON");
        JButton btnRefresh = new JButton("🔄 Rafraîchir");

        btnFilter.addActionListener(e -> showFilterDialog());
        btnDelete.addActionListener(e -> deleteSelectedEvent());
        btnExport.addActionListener(e -> exportToJson());
        btnImport.addActionListener(e -> importFromJson());
        btnRefresh.addActionListener(e -> refreshList());

        actionPanel.add(btnFilter);
        actionPanel.add(btnDelete);
        actionPanel.add(btnExport);
        actionPanel.add(btnImport);
        actionPanel.add(btnRefresh);

        JPanel buttonsWrapper = new JPanel(new GridLayout(2, 1));
        buttonsWrapper.add(addPanel);
        buttonsWrapper.add(actionPanel);

        statusLabel.setBorder(BorderFactory.createEmptyBorder(2, 8, 4, 8));
        southPanel.add(buttonsWrapper, BorderLayout.CENTER);
        southPanel.add(statusLabel, BorderLayout.SOUTH);

        add(southPanel, BorderLayout.SOUTH);
    }

    // ──────────────────────────────────────────────────────────────────────────
    // Fonctionnalité 1 : Ajouter un RendezVousPersonnel
    // ──────────────────────────────────────────────────────────────────────────
    private void showAddEventDialog() {
        JTextField titreField = new JTextField();
        JTextField heureField = new JTextField("10:00");
        JTextField dureeField = new JTextField("60");
        JTextField descField  = new JTextField();

        Object[] message = {
            "Titre :", titreField,
            "Heure début (HH:mm) :", heureField,
            "Durée (minutes) :", dureeField,
            "Description :", descField
        };

        if (JOptionPane.showConfirmDialog(this, message, "Nouveau Rendez-vous", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
            try {
                Evenement rdv = new fr.mycalendar.domain.event.RendezVousPersonnel(
                    EventId.nouveau(),
                    new TitreEvenement(titreField.getText()),
                    new DateEvenement(LocalDate.now()),
                    new HeureDebut(LocalTime.parse(heureField.getText())),
                    new DureeEvenement(Duration.ofMinutes(Long.parseLong(dureeField.getText()))),
                    new DescriptionEvenement(descField.getText())
                );
                calendrier.ajouter(rdv);
                setStatus("Rendez-vous ajouté ! ID: " + rdv.id().valeur());
                refreshList();
            } catch (EvenementEnConflitException ex) {
                JOptionPane.showMessageDialog(this, "⚠️ Conflit détecté : " + ex.getMessage(), "Conflit", JOptionPane.WARNING_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erreur : " + ex.getMessage());
            }
        }
    }

    // ──────────────────────────────────────────────────────────────────────────
    // Fonctionnalité 1 : Ajouter un Rappel (nouveau type)
    // ──────────────────────────────────────────────────────────────────────────
    private void showAddRappelDialog() {
        JTextField titreField = new JTextField();
        JTextField heureField = new JTextField("10:00");
        JTextField descField  = new JTextField();

        Object[] message = {
            "Titre :", titreField,
            "Heure (HH:mm) :", heureField,
            "Description :", descField
        };

        if (JOptionPane.showConfirmDialog(this, message, "Nouveau Rappel", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
            try {
                Evenement rappel = new fr.mycalendar.domain.event.Rappel(
                    EventId.nouveau(),
                    new TitreEvenement(titreField.getText()),
                    new DateEvenement(LocalDate.now()),
                    new HeureDebut(LocalTime.parse(heureField.getText())),
                    new DescriptionEvenement(descField.getText())
                );
                calendrier.ajouter(rappel);
                setStatus("Rappel ajouté ! ID: " + rappel.id().valeur());
                refreshList();
            } catch (EvenementEnConflitException ex) {
                JOptionPane.showMessageDialog(this, "⚠️ Conflit détecté : " + ex.getMessage(), "Conflit", JOptionPane.WARNING_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erreur : " + ex.getMessage());
            }
        }
    }

    // ──────────────────────────────────────────────────────────────────────────
    // Fonctionnalité 2 : Filtrer par période
    // ──────────────────────────────────────────────────────────────────────────
    private void showFilterDialog() {
        JTextField hDebut = new JTextField("09:00");
        JTextField hFin   = new JTextField("18:00");

        Object[] message = {
            "Heure de début (HH:mm) :", hDebut,
            "Heure de fin (HH:mm)   :", hFin
        };

        if (JOptionPane.showConfirmDialog(this, message, "Filtrer les événements d'aujourd'hui", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
            try {
                Periode periode = new Periode(
                    LocalDateTime.of(LocalDate.now(), LocalTime.parse(hDebut.getText())),
                    LocalDateTime.of(LocalDate.now(), LocalTime.parse(hFin.getText()))
                );
                List<Evenement> resultats = calendrier.evenementsPour(periode);
                listModel.clear();
                resultats.forEach(listModel::addElement);
                setStatus("Filtre actif : " + resultats.size() + " événement(s) trouvé(s). Cliquez Rafraîchir pour tout voir.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Format invalide. Utilisez HH:mm.");
            }
        }
    }

    // ──────────────────────────────────────────────────────────────────────────
    // Fonctionnalité 5 : Supprimer l'événement sélectionné (par EventId)
    // ──────────────────────────────────────────────────────────────────────────
    private void deleteSelectedEvent() {
        Evenement selected = eventList.getSelectedValue();
        if (selected != null) {
            int confirm = JOptionPane.showConfirmDialog(this,
                "Supprimer cet événement ?\n" + selected.description().valeur(),
                "Confirmation", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                calendrier.supprimer(selected.id());
                setStatus("Événement supprimé (ID: " + selected.id().valeur().substring(0, 8) + "…)");
                refreshList();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Sélectionnez un événement dans la liste.");
        }
    }

    // ──────────────────────────────────────────────────────────────────────────
    // Export JSON avec extension .json forcée
    // ──────────────────────────────────────────────────────────────────────────
    private void exportToJson() {
        try {
            fr.mycalendar.domain.calendar.CalendrierSerializer serializer = new fr.mycalendar.domain.calendar.CalendrierSerializer();
            String json = serializer.exporter(calendrier);

            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Exporter le calendrier en JSON");
            fileChooser.setFileFilter(new FileNameExtensionFilter("Fichier JSON (*.json)", "json"));
            fileChooser.setSelectedFile(new File("calendrier.json"));

            if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                // Forcer l'extension .json si absente
                if (!file.getName().toLowerCase().endsWith(".json")) {
                    file = new File(file.getAbsolutePath() + ".json");
                }
                java.nio.file.Files.writeString(file.toPath(), json);
                setStatus("Exporté vers : " + file.getName());
                JOptionPane.showMessageDialog(this, "Calendrier exporté !\n" + file.getAbsolutePath());
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erreur export : " + ex.getMessage());
        }
    }

    // ──────────────────────────────────────────────────────────────────────────
    // Import JSON
    // ──────────────────────────────────────────────────────────────────────────
    private void importFromJson() {
        try {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Importer un calendrier JSON");
            fileChooser.setFileFilter(new FileNameExtensionFilter("Fichier JSON (*.json)", "json"));

            if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                String json = java.nio.file.Files.readString(fileChooser.getSelectedFile().toPath());
                fr.mycalendar.domain.calendar.CalendrierSerializer serializer = new fr.mycalendar.domain.calendar.CalendrierSerializer();
                Calendrier importe = serializer.importer(json);
                for (Evenement e : importe.evenements()) {
                    try { calendrier.ajouter(e); } catch (EvenementEnConflitException ignored) {}
                }
                refreshList();
                setStatus("Importé depuis : " + fileChooser.getSelectedFile().getName());
                JOptionPane.showMessageDialog(this, "Calendrier importé !");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erreur import : " + ex.getMessage());
        }
    }

    private void refreshList() {
        listModel.clear();
        calendrier.evenements().forEach(listModel::addElement);
        setStatus(listModel.size() + " événement(s) au total.");
    }

    private void setStatus(String msg) {
        statusLabel.setText("ℹ️  " + msg);
    }
}
