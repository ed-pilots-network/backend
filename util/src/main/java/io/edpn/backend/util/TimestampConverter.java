package io.edpn.backend.util;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class TimestampConverter {

    private static final DateTimeFormatter FORMATTER_WITH_MS = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX");
    private static final DateTimeFormatter FORMATTER_WITHOUT_MS = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssX");

    public static LocalDateTime convertToLocalDateTime(String timestamp) {
        try {
            ZonedDateTime zonedDateTime = ZonedDateTime.parse(timestamp, FORMATTER_WITH_MS);
            return zonedDateTime.withZoneSameInstant(ZoneOffset.UTC).toLocalDateTime();
        } catch (DateTimeParseException e) {
            try {
                ZonedDateTime zonedDateTime = ZonedDateTime.parse(timestamp, FORMATTER_WITHOUT_MS);
                return zonedDateTime.withZoneSameInstant(ZoneOffset.UTC).toLocalDateTime();
            } catch (DateTimeParseException ex) {
                throw new IllegalArgumentException("Invalid timestamp format: " + timestamp, ex);
            }
        }
    }

}
