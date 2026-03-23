package fr.mycalendar.domain.vo;

import java.time.LocalDate;

public final class DateEvenement {
    private final LocalDate valeur;

    public DateEvenement(LocalDate valeur) {
        this.valeur = valeur;
    }

    public LocalDate valeur() {
        return valeur;
    }
}
