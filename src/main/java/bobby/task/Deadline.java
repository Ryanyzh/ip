package bobby.task;

import bobby.util.DateTimeParser;

import java.time.LocalDateTime;

/**
 * Represents a task that should be completed by a specific date or time.
 */
public class Deadline extends Task {
    private final LocalDateTime by;

    /**
     * Creates a deadline task.
     *
     * @param description text that describes the task
     * @param by deadline date/time
     */
    public Deadline(String description, LocalDateTime by) {
        super(description, TaskType.DEADLINE);
        this.by = by;
    }

    /**
     * Returns this deadline in the chatbot's list display format.
     *
     * @return deadline type, status, description, and deadline
     */
    @Override
    public String toString() {
        return super.toString() + " (by: " + DateTimeParser.formatForDisplay(by) + ")";
    }

    /**
     * Returns a line that can be saved in the data file.
     *
     * @return serialized deadline data
     */
    @Override
    public String toStorageString() {
        return super.toStorageString() + " | " + by;
    }
}
