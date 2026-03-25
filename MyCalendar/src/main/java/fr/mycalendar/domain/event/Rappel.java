package fr.mycalendar.domain.event;

import fr.mycalendar.domain.vo.*;
import java.time.LocalDateTime;

public final class Rappel implements Evenement {
    private final EventId id;
    private final TitreEvenement titre;
    private final DateEvenement date;
    private final HeureDebut heureDebut;
    private final DescriptionEvenement description;

    public Rappel(EventId id, TitreEvenement titre, DateEvenement date, HeureDebut heureDebut, DescriptionEvenement description) {
        this.id = id;
        this.titre = titre;
        this.date = date;
        this.heureDebut = heureDebut;
        this.description = description;
    }

    @Override
    public EventId id() {
        return id;
    }



    @Override
    public Periode periode() {
        LocalDateTime instant = LocalDateTime.of(date.valeur(), heureDebut.valeur());
        return new Periode(instant, instant); // Durée de zéro
    }

    @Override
    public DescriptionEvenement description() {
        return description;
    }
}
