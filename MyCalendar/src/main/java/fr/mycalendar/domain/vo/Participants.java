package fr.mycalendar.domain.vo;

import java.util.Collections;
import java.util.List;

public final class Participants {
    private final List<Participant> valeurs;

    public Participants(List<Participant> valeurs) {
        this.valeurs = Collections.unmodifiableList(valeurs);
    }

    public List<Participant> valeurs() {
        return valeurs;
    }
}
