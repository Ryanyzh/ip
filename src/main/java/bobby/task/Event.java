package bobby.task;

import bobby.util.DateTimeParser;

import java.time.LocalDateTime;

/**
 * Represents a task that happens from one date or time to another.
 */
public class Event extends Task {
    private final LocalDateTime from;
    private final LocalDateTime to;

    /**
     * Creates an event task.
     *
     * @param description text that describes the event
     * @param from start date/time
     * @param to end date/time
     */
    public Event(String description, LocalDateTime from, LocalDateTime to) {
        super(description, TaskType.EVENT);
        this.from = from;
        this.to = to;
    }

    /**
     * Returns this event in the chatbot's list display format.
     *
     * @return event type, status, description, start, and end
     */
    @Override
    public String toString() {
        return super.toString() + " (from: " + DateTimeParser.formatForDisplay(from)
                + " to: " + DateTimeParser.formatForDisplay(to) + ")";
    }

    /**
     * Returns a line that can be saved in the data file.
     *
     * @return serialized event data
     */
    @Override
    public String toStorageString() {
        return super.toStorageString() + " | " + from + " | " + to;
    }
}
