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
        
        buttonPanel.add(btnRefresh);
        buttonPanel.add(btnAdd);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void refreshList() {
        listModel.clear();
        for (Evenement e : calendrier.evenements()) {
            listModel.addElement(e);
        }
    }
}
