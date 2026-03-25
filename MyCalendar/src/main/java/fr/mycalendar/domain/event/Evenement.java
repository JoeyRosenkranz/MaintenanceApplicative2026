package fr.mycalendar.domain.event;

import fr.mycalendar.domain.vo.EventId;
import fr.mycalendar.domain.vo.Periode;

public interface Evenement {
    EventId id();
    default boolean estDansPeriode(Periode periode) {
        return this.periode().chevauche(periode);
    }
    Periode periode();
    fr.mycalendar.domain.vo.DescriptionEvenement description();
}
