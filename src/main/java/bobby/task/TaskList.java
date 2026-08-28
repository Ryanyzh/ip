package bobby.task;

import java.util.ArrayList;

/**
 * Stores the task list and provides operations that change or inspect it.
 */
public class TaskList {
    private final ArrayList<Task> tasks;

    /**
     * Creates a task list backed by the given tasks.
     *
     * @param tasks tasks loaded from storage
     */
    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Adds a task to the list.
     *
     * @param task task to add
     */
    public void add(Task task) {
        tasks.add(task);
    }

    /**
     * Returns tasks whose descriptions contain the given keyword.
     *
     * @param keyword text to search for
     * @return matching tasks in their current list order
     */
    public ArrayList<Task> find(String keyword) {
        ArrayList<Task> matchingTasks = new ArrayList<>();
        for (Task task : tasks) {
            if (task.containsKeyword(keyword)) {
                matchingTasks.add(task);
            }
        }
        return matchingTasks;
    }

    /**
     * Deletes a task by zero-based index.
     *
     * @param taskIndex index of task to delete
     * @return deleted task
     */
    public Task delete(int taskIndex) {
        return tasks.remove(taskIndex);
    }

    /**
     * Marks a task as done by zero-based index.
     *
     * @param taskIndex index of task to mark
     * @return marked task
     */
    public Task mark(int taskIndex) {
        Task task = tasks.get(taskIndex);
        task.markAsDone();
        return task;
    }

    /**
     * Marks a task as not done by zero-based index.
     *
     * @param taskIndex index of task to unmark
     * @return unmarked task
     */
    public Task unmark(int taskIndex) {
        Task task = tasks.get(taskIndex);
        task.markAsNotDone();
        return task;
    }

    /**
     * Returns whether the given zero-based task index exists.
     *
     * @param taskIndex index to check
     * @return true if the index points to an existing task
     */
    public boolean isValidIndex(int taskIndex) {
        return taskIndex >= 0 && taskIndex < tasks.size();
    }

    /**
     * Returns the current number of tasks.
     *
     * @return task count
     */
    public int size() {
        return tasks.size();
    }

    /**
     * Returns the backing list for display and storage.
     *
     * @return current tasks
     */
    public ArrayList<Task> asList() {
        return tasks;
    }
}
