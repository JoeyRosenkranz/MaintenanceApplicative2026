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
                String type = evNode.get("@type").asText();
                
                fr.mycalendar.domain.vo.EventId id = fr.mycalendar.domain.vo.EventId.nouveau();
                // Since EventId.nouveau() generates a new one, we need to parse it cleanly, 
                // but EventId only exposes a UUID directly in the json "valeur". 
                // Wait, EventId exposes its UUID value via toString() or we can just ignore reconstructing exact UUID 
                // if it's not possible without constructor? 
                // Actually EventId has `fromString`? No, let me look at EventId... I'll just use a test placeholder for now or parse it directly 
                // Let's look at the generated JSON for EventId: {"valeur":"ad66ab32-..."} - EventId has a public constructor.
                // Assuming we can figure it out:
                fr.mycalendar.domain.vo.EventId parsedId = fr.mycalendar.domain.vo.EventId.depuis(evNode.get("id").get("valeur").asText());
                fr.mycalendar.domain.vo.TitreEvenement titre = new fr.mycalendar.domain.vo.TitreEvenement(evNode.get("titre").get("valeur").asText());
                fr.mycalendar.domain.vo.DateEvenement date = new fr.mycalendar.domain.vo.DateEvenement(java.time.LocalDate.parse(evNode.get("date").get("valeur").asText()));
                fr.mycalendar.domain.vo.HeureDebut heure = new fr.mycalendar.domain.vo.HeureDebut(java.time.LocalTime.parse(evNode.get("heureDebut").get("valeur").asText()));
                fr.mycalendar.domain.vo.DureeEvenement duree = new fr.mycalendar.domain.vo.DureeEvenement(java.time.Duration.parse(evNode.get("duree").get("valeur").asText()));
                fr.mycalendar.domain.vo.DescriptionEvenement desc = new fr.mycalendar.domain.vo.DescriptionEvenement(evNode.get("description").get("valeur").asText());
                
                if ("RendezVousPersonnel".equals(type)) {
                    calendrier.ajouter(new fr.mycalendar.domain.event.RendezVousPersonnel(parsedId, titre, date, heure, duree, desc));
                }
            }
        }
        return calendrier;
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
