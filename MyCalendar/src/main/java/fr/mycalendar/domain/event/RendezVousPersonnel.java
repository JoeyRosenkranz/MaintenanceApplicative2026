package fr.mycalendar.domain.event;

import fr.mycalendar.domain.vo.*;

public final class RendezVousPersonnel implements Evenement {
    private final EventId id;
    private final TitreEvenement titre;
    private final DateEvenement date;
    private final HeureDebut heureDebut;
    private final DureeEvenement duree;
    private final DescriptionEvenement description;

    public RendezVousPersonnel(EventId id, TitreEvenement titre, DateEvenement date, HeureDebut heureDebut, DureeEvenement duree, DescriptionEvenement description) {
        this.id = id;
        this.titre = titre;
        this.date = date;
        this.heureDebut = heureDebut;
        this.duree = duree;
        this.description = description;
    }

    @Override
    public EventId id() {
        return id;
    }

    public boolean estDansPeriode(Periode periode) {
        java.time.LocalDateTime debut = java.time.LocalDateTime.of(date.valeur(), heureDebut.valeur());
        java.time.LocalDateTime fin = debut.plus(duree.valeur());
        return !fin.isBefore(periode.getDebut()) && !debut.isAfter(periode.getFin());
    }

    @Override
    public Periode periode() {
        java.time.LocalDateTime debut = java.time.LocalDateTime.of(date.valeur(), heureDebut.valeur());
        java.time.LocalDateTime fin = debut.plus(duree.valeur());
        return new Periode(debut, fin);
    }

    @Override
    public String description() {
        return description.valeur();
    }
}
