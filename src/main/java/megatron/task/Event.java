package megatron.task;
import java.time.LocalDateTime;

/** Represents a task with a start time and an end time. */
public class Event extends Task {
    private final LocalDateTime from;
    private final LocalDateTime to;

    /**
     * Creates an event task.
     *
     * @param description the text describing the event
     * @param from the event's start time text
     * @param to the event's end time text
     */
    public Event(String description, String from, String to) {
        this(description, DateTimeParser.parse(from), DateTimeParser.parse(to));
    }

    /** Creates an event task with typed start and end dates and times. */
    public Event(String description, LocalDateTime from, LocalDateTime to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    @Override
    /** Returns the serialized display representation of this event. */
    public String toString() {
        return "[E][" + getStatusIcon() + "] " + description + " (from: " + DateTimeParser.format(from)
                + " to: " + DateTimeParser.format(to) + ")";
    }
}
