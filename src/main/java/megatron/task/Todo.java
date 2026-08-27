package megatron.task;
/**
 * Represents a task with no date or time attached to it.
 */
public class Todo extends Task {
    /**
     * Creates a to-do task.
     *
     * @param description the text describing the task
     */
    /** Creates a to-do task. */
    public Todo(String description) {
        super(description);
    }

    @Override
    /** Returns the serialized display representation of this task. */
    public String toString() {
        return "[T][" + getStatusIcon() + "] " + description;
    }
}
