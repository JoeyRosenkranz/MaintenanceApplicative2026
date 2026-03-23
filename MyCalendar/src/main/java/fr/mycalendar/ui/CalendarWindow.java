package fr.mycalendar.ui;

import fr.mycalendar.domain.calendar.Calendrier;
import fr.mycalendar.domain.event.Evenement;

import javax.swing.*;
import java.awt.*;

public class CalendarWindow extends JFrame {
    private final Calendrier calendrier;
    private final DefaultListModel<Evenement> listModel;
    private final JList<Evenement> eventList;

    public CalendarWindow(Calendrier calendrier) {
        this.calendrier = calendrier;
        this.listModel = new DefaultListModel<>();
        this.eventList = new JList<>(listModel);
        
        setTitle("MyCalendar - TDD Strict");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
        
        setupComponents();
        refreshList();
    }

    private void setupComponents() {
        setLayout(new BorderLayout(10, 10));
        
        // Liste des événements
        eventList.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Evenement) {
                    Evenement e = (Evenement) value;
                    label.setText("[" + e.periode().getDebut().toLocalTime() + "] " + e.description());
                }
                label.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
                return label;
            }
        });
        
        JScrollPane scrollPane = new JScrollPane(eventList);
        add(scrollPane, BorderLayout.CENTER);
        
        // Pannneau de boutons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnAdd = new JButton("Ajouter un rendez-vous");
        JButton btnRefresh = new JButton("Rafraîchir");
        
        btnRefresh.addActionListener(e -> refreshList());
        btnAdd.addActionListener(e -> showAddEventDialog());
        
        buttonPanel.add(btnRefresh);
        buttonPanel.add(btnAdd);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void showAddEventDialog() {
        JTextField titreField = new JTextField();
        JTextField heureField = new JTextField("10:00");
        JTextField descField = new JTextField();
        
        Object[] message = {
            "Titre:", titreField,
            "Heure début (HH:mm):", heureField,
            "Description:", descField
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Nouveau Rendez-vous", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                fr.mycalendar.domain.event.Evenement rdv = new fr.mycalendar.domain.event.RendezVousPersonnel(
                    fr.mycalendar.domain.vo.EventId.nouveau(),
                    new fr.mycalendar.domain.vo.TitreEvenement(titreField.getText()),
                    new fr.mycalendar.domain.vo.DateEvenement(java.time.LocalDate.now()),
                    new fr.mycalendar.domain.vo.HeureDebut(java.time.LocalTime.parse(heureField.getText())),
                    new fr.mycalendar.domain.vo.DureeEvenement(java.time.Duration.ofMinutes(60)),
                    new fr.mycalendar.domain.vo.DescriptionEvenement(descField.getText())
                );
                calendrier.ajouter(rdv);
                refreshList();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Erreur de saisie : " + e.getMessage());
            }
        }
    }

    private void refreshList() {
        listModel.clear();
        for (Evenement e : calendrier.evenements()) {
            listModel.addElement(e);
        }
    }
}
