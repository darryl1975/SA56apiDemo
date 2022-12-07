package sg.edu.nus.iss.demo.serializer;

import java.io.IOException;
import java.time.DateTimeException;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class OffsetDateTimeSerializer extends JsonSerializer<OffsetDateTime> {
    @Override
    public void serialize(OffsetDateTime dateTime, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
            throws IOException {
        try {
            String dateString = DateTimeFormatter.ISO_INSTANT.format(dateTime);
            jsonGenerator.writeString(dateString);
        } catch (DateTimeException e) {
            jsonGenerator.writeString("DateTimeException");
        }
    }
}