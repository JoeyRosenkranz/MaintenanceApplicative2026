package fr.mycalendar.domain.vo;

import java.util.UUID;

public final class EventId {
    private final String valeur;

    private EventId(String valeur) {
        this.valeur = valeur;
    }

    public static EventId nouveau() {
        return new EventId(UUID.randomUUID().toString());
    }

    public String valeur() {
        return valeur;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventId eventId = (EventId) o;
        return valeur.equals(eventId.valeur);
    }

    @Override
    public int hashCode() {
        return valeur.hashCode();
    }
}
