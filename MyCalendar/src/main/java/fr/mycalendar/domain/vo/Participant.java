package fr.mycalendar.domain.vo;

public final class Participant {
    private final String nom;

    public Participant(String nom) {
        this.nom = nom;
    }

    public String nom() {
        return nom;
    }
}
