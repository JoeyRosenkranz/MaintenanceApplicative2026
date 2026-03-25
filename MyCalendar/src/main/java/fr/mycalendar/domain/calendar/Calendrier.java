package fr.mycalendar.domain.calendar;

import fr.mycalendar.domain.event.Evenement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class Calendrier {
    private final List<Evenement> evenements;

    public Calendrier() {
        this.evenements = new ArrayList<>();
    }

    public List<Evenement> evenements() {
        return Collections.unmodifiableList(evenements);
    }

    public void ajouter(Evenement evenement) {
        evenements.stream()
                .filter(e -> detecterConflits(e, evenement))
                .findAny()
                .ifPresent(e -> {
                    throw new EvenementEnConflitException("L'événement entre en conflit avec un événement existant.");
                });
        this.evenements.add(evenement);
    }

    public List<Evenement> evenementsPour(fr.mycalendar.domain.vo.Periode periode) {
        return evenements.stream()
                .filter(e -> e.estDansPeriode(periode))
                .toList();
    }

    public boolean detecterConflits(Evenement e1, Evenement e2) {
        return e1.periode().chevauche(e2.periode());
    }

    public void supprimer(fr.mycalendar.domain.vo.EventId id) {
        evenements.removeIf(e -> e.id().equals(id));
    }
}
