package fr.mycalendar.domain.event;

import fr.mycalendar.domain.vo.EventId;
import fr.mycalendar.domain.vo.Periode;

public interface Evenement {
    EventId id();
    boolean estDansPeriode(Periode periode);
    Periode periode();
}
