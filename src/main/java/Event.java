/**
 * Represents a task with a start time and an end time.
 */
public class Event extends Task {
    private final String from;
    private final String to;

    /**
     * Creates an event task.
     *
     * @param description the text describing the event
     * @param from the event's start time text
     * @param to the event's end time text
     */
    public Event(String description, String from, String to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    @Override
    public String toString() {
        return "[E][" + getStatusIcon() + "] " + description + " (from: " + from + " to: " + to + ")";
    }
}
