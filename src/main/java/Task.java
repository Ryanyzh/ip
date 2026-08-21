/**
 * Represents one task in Bobby's in-memory task list.
 */
public class Task {
    private final String description;
    private boolean isDone;

    /**
     * Creates a task that starts out as not done.
     *
     * @param description text entered by the user to describe the task
     */
    public Task(String description) {
        this.description = description;
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
        return "[" + getStatusIcon() + "] " + description;
    }

    private String getStatusIcon() {
        return isDone ? "X" : " ";
    }
}
