package bobby.util;

import bobby.BobbyException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

import bobby.BobbyException;

/**
 * Parses and formats date/time values used by deadline and event tasks.
 */
public class DateTimeParser {
    private static final DateTimeFormatter DISPLAY_DATE = DateTimeFormatter.ofPattern("MMM d yyyy", Locale.ENGLISH);
    private static final DateTimeFormatter DISPLAY_DATE_TIME = DateTimeFormatter.ofPattern(
            "MMM d yyyy, h:mma", Locale.ENGLISH);
    private static final DateTimeFormatter SLASH_DATE_TIME = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
    private static final DateTimeFormatter DASH_DATE_TIME = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
    private static final DateTimeFormatter ISO_DATE_TIME = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    /**
     * Parses a user-entered date or date-time into a LocalDateTime.
     *
     * @param input date/time text entered by the user.
     * @return parsed date/time.
     * @throws BobbyException if the input is not in a supported format.
     */
    public static LocalDateTime parse(String input) throws BobbyException {
        String trimmedInput = input.trim();
        LocalDateTime parsedDateTime = parseDateTime(trimmedInput);
        if (parsedDateTime != null) {
            return parsedDateTime;
        }

        try {
            return LocalDate.parse(trimmedInput).atStartOfDay();
        } catch (DateTimeParseException e) {
            throw new BobbyException("Please use a date format like 2019-12-02 or 2/12/2019 1800.");
        }
    }

    /**
     * Formats a date/time for display to the user.
     *
     * @param dateTime date/time to format.
     * @return readable date/time.
     */
    public static String formatForDisplay(LocalDateTime dateTime) {
        if (dateTime.toLocalTime().equals(LocalTime.MIDNIGHT)) {
            return dateTime.format(DISPLAY_DATE);
        }
        return dateTime.format(DISPLAY_DATE_TIME).replace("AM", "am").replace("PM", "pm");
    }

    /**
     * Tries each supported date-time pattern that includes both date and time components.
     *
     * @param input trimmed date-time text.
     * @return parsed date-time, or null if none of the supported patterns match.
     */
    private static LocalDateTime parseDateTime(String input) {
        DateTimeFormatter[] formatters = {ISO_DATE_TIME, DASH_DATE_TIME, SLASH_DATE_TIME};
        for (DateTimeFormatter formatter : formatters) {
            try {
                return LocalDateTime.parse(input, formatter);
            } catch (DateTimeParseException e) {
                // Try the next supported format.
            }
        }
        return null;
    }
}
