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
}
