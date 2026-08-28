package bobby.ui;

import java.util.ArrayList;
import java.util.Scanner;

import bobby.task.Task;

/**
 * Handles console interactions with the user.
 */
public class Ui {
    private static final String LINE = "____________________________________________________________";
    private static final String BANNER = " ____        _     _           \n"
            + "| __ )  ___ | |__ | |__  _   _ \n"
            + "|  _ \\ / _ \\| '_ \\| '_ \\| | | |\n"
            + "| |_) | (_) | |_) | |_) | |_| |\n"
            + "|____/ \\___/|_.__/|_.__/ \\__, |\n"
            + "                         |___/ \n";

    private final Scanner scanner;

    /**
     * Creates a UI that reads user input from standard input.
     */
    public Ui() {
        scanner = new Scanner(System.in);
    }

    /**
     * Greets the user when Bobby starts.
     */
    public void showWelcome() {
        showLine();
        System.out.print(BANNER);
        System.out.println("Hello! I'm Bobby.");
        System.out.println("What can I do for you?");
        showLine();
    }

    /**
     * Returns whether another line of user input is available.
     *
     * @return true if there is another input line.
     */
    public boolean hasNextCommand() {
        return scanner.hasNextLine();
    }

    /**
     * Reads and trims the next command from the user.
     *
     * @return trimmed command text.
     */
    public String readCommand() {
        return scanner.nextLine().trim();
    }

    /**
     * Prints the goodbye message.
     */
    public void showGoodbye() {
        System.out.println("Goodbye! Bobby signing out...");
    }

    /**
     * Prints an error from a user command.
     *
     * @param message error details.
     */
    public void showCommandError(String message) {
        System.out.println("Bobby needs a clearer command: " + message);
    }

    /**
     * Prints an error from loading the save file.
     *
     * @param message error details.
     */
    public void showLoadingError(String message) {
        System.out.println("Bobby needs a clearer save file: " + message);
    }

    /**
     * Prints all tasks in the list.
     *
     * @param tasks tasks to display.
     */
    public void showTaskList(ArrayList<Task> tasks) {
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + "." + tasks.get(i));
        }
    }

    /**
     * Prints all tasks that match a find command.
     *
     * @param tasks matching tasks to display
     */
    public void showMatchingTasks(ArrayList<Task> tasks) {
        System.out.println("Here are the matching tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + "." + tasks.get(i));
        }
    }

    /**
     * Prints the message shown after a task is added.
     *
     * @param task added task.
     * @param taskCount current number of tasks.
     */
    public void showTaskAdded(Task task, int taskCount) {
        System.out.println("Got it. I've added this task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + taskCount + " tasks in the list.");
    }

    /**
     * Prints the message shown after a task is deleted.
     *
     * @param task deleted task.
     * @param taskCount current number of tasks.
     */
    public void showTaskDeleted(Task task, int taskCount) {
        System.out.println("Noted. I've removed this task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + taskCount + " tasks in the list.");
    }

    /**
     * Prints the message shown after a task is marked as done.
     *
     * @param task marked task.
     */
    public void showTaskMarked(Task task) {
        System.out.println("Nice! I've marked this task as done:");
        System.out.println("  " + task);
    }

    /**
     * Prints the message shown after a task is marked as not done.
     *
     * @param task unmarked task.
     */
    public void showTaskUnmarked(Task task) {
        System.out.println("OK, I've marked this task as not done yet:");
        System.out.println("  " + task);
    }

    /**
     * Prints the common horizontal divider.
     */
    public void showLine() {
        System.out.println(LINE);
    }
}
