package sg.edu.nus.iss.demo.serializer;

import java.io.IOException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class LocalDateSerializer extends JsonSerializer<LocalDate> {
    @Override
    public void serialize(LocalDate date, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
            throws IOException {
        try {
            String dateString = DateTimeFormatter.ISO_LOCAL_DATE.format(date);
            jsonGenerator.writeString(dateString);
        } catch (DateTimeException e) {
            jsonGenerator.writeString("DateTimeException");
        }
    }
}
