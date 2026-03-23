package fr.mycalendar.domain.calendar;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class CalendrierSerializer {
    private final ObjectMapper mapper;

    public CalendrierSerializer() {
        this.mapper = new ObjectMapper();
        this.mapper.registerModule(new JavaTimeModule());
        this.mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        this.mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        this.mapper.disable(SerializationFeature.WRITE_DURATIONS_AS_TIMESTAMPS);
    }

    public String exporter(Object obj) throws Exception {
        return mapper.writeValueAsString(obj);
    }
}
