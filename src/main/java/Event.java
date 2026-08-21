/**
 * Represents a task that happens from one date or time to another.
 */
public class Event extends Task {
    private final String from;
    private final String to;

    /**
     * Creates an event task.
     *
     * @param description text that describes the event
     * @param from start date or time entered by the user
     * @param to end date or time entered by the user
     */
    public Event(String description, String from, String to) {
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
        return super.toString() + " (from: " + from + " to: " + to + ")";
    }
}
