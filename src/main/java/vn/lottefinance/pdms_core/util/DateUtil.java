package vn.lottefinance.pdms_core.util;

import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateUtil {
    public static long getUnixTimestamp(Date date) {
        return date.getTime() / 1000;
    }

    public static String convertDateToString(LocalDate date, String format) {
        // Define the formatter with the desired pattern
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        // Format the date to the desired pattern
        String formattedDate = date.format(formatter);
        return formattedDate;
    }

    public static String convertDateToString(Instant date, String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format).withZone(ZoneId.of("UTC"));
        return formatter.format(date);
    }

    public static String convertLocalDateToUTCString(LocalDate localDate) {
        return localDate
                .atStartOfDay(ZoneOffset.UTC) // Start at midnight in UTC
                .format(DateTimeFormatter.ISO_INSTANT); // Format as ISO-8601
    }

    public static String convertToISO8601(String dateStr) throws ParseException {
        if (StringUtils.isEmpty(dateStr)) {
            return null;
        }
        // Input format
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate localDate = LocalDate.parse(dateStr, inputFormatter);

        // Step 2: Convert LocalDate to LocalDateTime (set time to 00:00)
        LocalDateTime localDateTime = localDate.atStartOfDay();

        // Step 3: Format the LocalDateTime to the desired ISO 8601 format
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        String iso8601Date = localDateTime.format(outputFormatter);
        return iso8601Date;
    }
}
