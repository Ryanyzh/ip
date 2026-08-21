/**
 * Represents a task that should be completed by a specific date or time.
 */
public class Deadline extends Task {
    private final String by;

    /**
     * Creates a deadline task.
     *
     * @param description text that describes the task
     * @param by deadline entered by the user
     */
    public Deadline(String description, String by) {
        super(description);
        this.by = by;
    }

    /**
     * Returns this deadline in the chatbot's list display format.
     *
     * @return deadline type, status, description, and deadline
     */
    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by + ")";
    }
}
