package fr.mycalendar.domain.vo;

import java.time.LocalTime;

public final class HeureDebut {
    private final LocalTime valeur;

    public HeureDebut(LocalTime valeur) {
        this.valeur = valeur;
    }

    public LocalTime valeur() {
        return valeur;
    }
}
