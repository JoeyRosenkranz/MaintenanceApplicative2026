package fr.mycalendar.domain.vo;

import java.time.LocalDateTime;

public final class Periode {
    private final LocalDateTime debut;
    private final LocalDateTime fin;

    public Periode(LocalDateTime debut, LocalDateTime fin) {
        this.debut = debut;
        this.fin = fin;
    }

    public LocalDateTime getDebut() {
        return debut;
    }

    public LocalDateTime getFin() {
        return fin;
    }

    public boolean chevauche(Periode autre) {
        return !this.fin.isBefore(autre.getDebut()) && !this.debut.isAfter(autre.getFin());
    }
}
