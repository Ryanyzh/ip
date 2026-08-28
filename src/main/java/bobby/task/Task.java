package bobby.task;

import java.util.Locale;

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
     * @param type category of the task
     */
    public Task(String description, TaskType type) {
        this.description = description;
        this.type = type;
        this.isDone = false;
    }

    /**
     * Returns a line that can be saved in the data file.
     *
     * @return serialized task data
     */
    public String toStorageString() {
        return type.getSymbol() + " | " + getStorageStatus() + " | " + description;
    }

    /**
     * Returns the task description entered by the user.
     *
     * @return task description
     */
    protected String getDescription() {
        return description;
    }

    /**
     * Returns whether this task's description contains the given keyword.
     *
     * @param keyword text to search for
     * @return true if the description contains the keyword
     */
    public boolean containsKeyword(String keyword) {
        return description.toLowerCase(Locale.ROOT).contains(keyword.toLowerCase(Locale.ROOT));
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
        return "[" + type.getSymbol() + "][" + getStatusIcon() + "] " + description;
    }

    private String getStatusIcon() {
        return isDone ? "X" : " ";
    }

    private String getStorageStatus() {
        return isDone ? "1" : "0";
    }
}
