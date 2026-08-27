package megatron.task;
import java.time.LocalDateTime;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

/** Parses the date and time formats accepted by Megatron. */
public final class DateTimeParser {
    private static final List<DateTimeFormatter> INPUT_FORMATS = List.of(
            DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"),
            DateTimeFormatter.ofPattern("d/M/yyyy HHmm"),
            DateTimeFormatter.ofPattern("yyyy-MM-dd"),
            DateTimeFormatter.ofPattern("MMM d yyyy HHmm"),
            DateTimeFormatter.ofPattern("MMM d yyyy"));
    private static final DateTimeFormatter DISPLAY_WITH_TIME =
            DateTimeFormatter.ofPattern("MMM d yyyy HHmm");
    private static final DateTimeFormatter DISPLAY_DATE_ONLY =
            DateTimeFormatter.ofPattern("MMM d yyyy");

    private DateTimeParser() {
    }

    /** Converts user input into a date-time, using midnight when no time is given. */
    public static LocalDateTime parse(String text) {
        boolean hasTime = text.matches(".*\\s\\d{4}$") || text.matches(".*\\s\\d{2}:\\d{2}$");
        for (DateTimeFormatter formatter : INPUT_FORMATS) {
            try {
                if (hasTime) {
                    return LocalDateTime.parse(text, formatter);
                }
                return LocalDate.parse(text, formatter).atStartOfDay();
            } catch (DateTimeParseException ignored) {
                // Try the next supported format.
            }
        }
        throw new IllegalArgumentException("Invalid date/time: " + text);
    }

    /** Formats a date-time for display while retaining the time when one was supplied. */
    public static String format(LocalDateTime value) {
        return value.toLocalTime().equals(LocalTime.MIDNIGHT)
                ? value.format(DISPLAY_DATE_ONLY) : value.format(DISPLAY_WITH_TIME);
    }
}
