package fr.mycalendar.domain.calendar;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;

public class CalendrierSerializer {
    private final ObjectMapper mapper;

    public CalendrierSerializer() {
        this.mapper = new ObjectMapper();
        this.mapper.registerModule(new JavaTimeModule());
        this.mapper.registerModule(new ParameterNamesModule(com.fasterxml.jackson.annotation.JsonCreator.Mode.PROPERTIES));
        this.mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        this.mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        this.mapper.disable(SerializationFeature.WRITE_DURATIONS_AS_TIMESTAMPS);
        this.mapper.addMixIn(fr.mycalendar.domain.event.Evenement.class, EvenementMixIn.class);
    }

    public String exporter(Object obj) throws Exception {
        return mapper.writeValueAsString(obj);
    }

    public fr.mycalendar.domain.calendar.Calendrier importer(String json) throws Exception {
        com.fasterxml.jackson.databind.JsonNode root = mapper.readTree(json);
        fr.mycalendar.domain.calendar.Calendrier calendrier = new fr.mycalendar.domain.calendar.Calendrier();
        com.fasterxml.jackson.databind.JsonNode evenements = root.get("evenements");
        
        if (evenements != null && evenements.isArray()) {
            for (com.fasterxml.jackson.databind.JsonNode evNode : evenements) {
                calendrier.ajouter(extraireEvenement(evNode));
            }
        }
        return calendrier;
    }

    private fr.mycalendar.domain.event.Evenement extraireEvenement(com.fasterxml.jackson.databind.JsonNode evNode) {
        String type = evNode.get("@type").asText();
        
        if ("EvenementPeriodique".equals(type)) {
            fr.mycalendar.domain.event.Evenement base = extraireEvenement(evNode.get("evenementDeBase"));
            fr.mycalendar.domain.vo.FrequenceRepetition freq = fr.mycalendar.domain.vo.FrequenceRepetition.valueOf(evNode.get("frequence").asText());
            return new fr.mycalendar.domain.event.EvenementPeriodique(base, freq);
        }

        fr.mycalendar.domain.vo.EventId id = fr.mycalendar.domain.vo.EventId.depuis(evNode.get("id").get("valeur").asText());
        fr.mycalendar.domain.vo.TitreEvenement titre = new fr.mycalendar.domain.vo.TitreEvenement(evNode.get("titre").get("valeur").asText());
        fr.mycalendar.domain.vo.DateEvenement date = new fr.mycalendar.domain.vo.DateEvenement(java.time.LocalDate.parse(evNode.get("date").get("valeur").asText()));
        fr.mycalendar.domain.vo.HeureDebut heure = new fr.mycalendar.domain.vo.HeureDebut(java.time.LocalTime.parse(evNode.get("heureDebut").get("valeur").asText()));
        fr.mycalendar.domain.vo.DureeEvenement duree = new fr.mycalendar.domain.vo.DureeEvenement(java.time.Duration.parse(evNode.get("duree").get("valeur").asText()));
        fr.mycalendar.domain.vo.DescriptionEvenement desc = new fr.mycalendar.domain.vo.DescriptionEvenement(evNode.get("description").get("valeur").asText());

        if ("RendezVousPersonnel".equals(type)) {
            return new fr.mycalendar.domain.event.RendezVousPersonnel(id, titre, date, heure, duree, desc);
        } else if ("Reunion".equals(type)) {
            fr.mycalendar.domain.vo.Lieu lieu = new fr.mycalendar.domain.vo.Lieu(evNode.get("lieu").get("valeur").asText());
            java.util.List<fr.mycalendar.domain.vo.Participant> pList = new java.util.ArrayList<>();
            com.fasterxml.jackson.databind.JsonNode pNodes = evNode.get("participants").get("valeurs");
            for (com.fasterxml.jackson.databind.JsonNode pNode : pNodes) {
                pList.add(new fr.mycalendar.domain.vo.Participant(pNode.get("nom").asText()));
            }
            return new fr.mycalendar.domain.event.Reunion(id, titre, date, heure, duree, desc, lieu, new fr.mycalendar.domain.vo.Participants(pList));
        }
        
        throw new IllegalArgumentException("Type d'événement inconnu : " + type);
    }

    public ObjectMapper getMapper() {
        return mapper;
    }

    @com.fasterxml.jackson.annotation.JsonTypeInfo(
        use = com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME, 
        include = com.fasterxml.jackson.annotation.JsonTypeInfo.As.PROPERTY, 
        property = "@type"
    )
    @com.fasterxml.jackson.annotation.JsonSubTypes({
        @com.fasterxml.jackson.annotation.JsonSubTypes.Type(value = fr.mycalendar.domain.event.RendezVousPersonnel.class, name = "RendezVousPersonnel"),
        @com.fasterxml.jackson.annotation.JsonSubTypes.Type(value = fr.mycalendar.domain.event.Reunion.class, name = "Reunion"),
        @com.fasterxml.jackson.annotation.JsonSubTypes.Type(value = fr.mycalendar.domain.event.EvenementPeriodique.class, name = "EvenementPeriodique")
    })
    private abstract static class EvenementMixIn {}
}
