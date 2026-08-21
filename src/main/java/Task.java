/**
 * Represents one task in Bobby's in-memory task list.
 */
public class Task {
    private final String description;
    private final TaskType type;
    private boolean isDone;

    /**
     * Creates a task that starts out as not done.
     *
     * @param description text entered by the user to describe the task
     */
    public Task(String description) {
        this(description, null);
    }

    /**
     * Creates a task that starts out as not done.
     *
     * @param description text entered by the user to describe the task
     * @param type category of the task
     */
    public Task(String description, TaskType type) {
        this.description = description;
        this.type = type;
        this.isDone = false;
    }

    /**
     * Marks this task as completed.
     */
    public void markAsDone() {
        isDone = true;
    }

    /**
     * Marks this task as not completed.
     */
    public void markAsNotDone() {
        isDone = false;
    }

    /**
     * Returns this task in the display format expected by the chatbot.
     *
     * @return task status and description
     */
    @Override
    public String toString() {
        if (type == null) {
            return "[" + getStatusIcon() + "] " + description;
        }
        return "[" + type.getSymbol() + "][" + getStatusIcon() + "] " + description;
    }

    private String getStatusIcon() {
        return isDone ? "X" : " ";
    }
}
