/**
 * Represents a task without any date or time attached.
 */
public class Todo extends Task {
    /**
     * Creates a todo task.
     *
     * @param description text that describes the todo
     */
    public Todo(String description) {
        super(description);
    }

    /**
     * Returns this todo in the chatbot's list display format.
     *
     * @return todo type, status, and description
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
