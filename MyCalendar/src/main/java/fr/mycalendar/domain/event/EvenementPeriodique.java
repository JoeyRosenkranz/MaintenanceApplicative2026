package fr.mycalendar.domain.event;

import fr.mycalendar.domain.vo.EventId;
import fr.mycalendar.domain.vo.FrequenceRepetition;

public final class EvenementPeriodique implements Evenement {
    private final Evenement evenementDeBase;
    private final FrequenceRepetition frequence;

    public EvenementPeriodique(Evenement evenementDeBase, FrequenceRepetition frequence) {
        this.evenementDeBase = evenementDeBase;
        this.frequence = frequence;
    }

    @Override
    public EventId id() {
        return evenementDeBase.id();
    }

    @Override
    public boolean estDansPeriode(fr.mycalendar.domain.vo.Periode periode) {
        return evenementDeBase.estDansPeriode(periode);
    }

    @Override
    public fr.mycalendar.domain.vo.Periode periode() {
        return evenementDeBase.periode();
    }

    @Override
    public fr.mycalendar.domain.vo.DescriptionEvenement description() {
        return new fr.mycalendar.domain.vo.DescriptionEvenement(evenementDeBase.description().valeur() + " (" + frequence.name() + ")");
    }
}
