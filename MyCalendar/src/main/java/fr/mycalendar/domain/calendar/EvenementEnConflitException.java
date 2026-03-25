package fr.mycalendar.domain.calendar;

public class EvenementEnConflitException extends RuntimeException {
    public EvenementEnConflitException(String message) {
        super(message);
    }
}
