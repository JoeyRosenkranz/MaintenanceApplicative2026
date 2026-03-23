package fr.mycalendar.domain.event;

import fr.mycalendar.domain.vo.*;

public final class Reunion implements Evenement {
    private final EventId id;
    private final TitreEvenement titre;
    private final DateEvenement date;
    private final HeureDebut heureDebut;
    private final DureeEvenement duree;
    private final DescriptionEvenement description;
    private final Lieu lieu;
    private final Participants participants;

    public Reunion(EventId id, TitreEvenement titre, DateEvenement date, HeureDebut heureDebut, DureeEvenement duree, DescriptionEvenement description, Lieu lieu, Participants participants) {
        this.id = id;
        this.titre = titre;
        this.date = date;
        this.heureDebut = heureDebut;
        this.duree = duree;
        this.description = description;
        this.lieu = lieu;
        this.participants = participants;
    }

    @Override
    public EventId id() {
        return id;
    }
}
