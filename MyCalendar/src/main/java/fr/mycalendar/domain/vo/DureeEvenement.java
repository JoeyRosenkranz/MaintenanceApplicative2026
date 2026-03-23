package fr.mycalendar.domain.vo;

import java.time.Duration;

public final class DureeEvenement {
    private final Duration valeur;

    public DureeEvenement(Duration valeur) {
        this.valeur = valeur;
    }

    public Duration valeur() {
        return valeur;
    }
}
