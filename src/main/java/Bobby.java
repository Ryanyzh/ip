import java.util.Scanner;

/**
 * Entry point for the Bobby chatbot application.
 */
public class Bobby {
    private static final String LINE = "____________________________________________________________";
    private static final String BYE_COMMAND = "bye";
    private static final String LIST_COMMAND = "list";
    private static final String MARK_COMMAND = "mark";
    private static final String UNMARK_COMMAND = "unmark";
    private static final String TODO_COMMAND = "todo";
    private static final String DEADLINE_COMMAND = "deadline";
    private static final String EVENT_COMMAND = "event";
    private static final String BY_SEPARATOR = " /by ";
    private static final String FROM_SEPARATOR = " /from ";
    private static final String TO_SEPARATOR = " /to ";
    private static final int MAX_TASKS = 100;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Task[] tasks = new Task[MAX_TASKS];
        int taskCount = 0;
        String banner = " ____        _     _           \n"
                + "| __ )  ___ | |__ | |__  _   _ \n"
                + "|  _ \\ / _ \\| '_ \\| '_ \\| | | |\n"
                + "| |_) | (_) | |_) | |_) | |_| |\n"
                + "|____/ \\___/|_.__/|_.__/ \\__, |\n"
                + "                         |___/ \n";
        System.out.println(LINE);
        System.out.print(banner);
        System.out.println("Hello! I'm Bobby.");
        System.out.println("What can I do for you?");
        System.out.println(LINE);

        while (scanner.hasNextLine()) {
            String command = scanner.nextLine();
            System.out.println(LINE);

            if (command.equals(BYE_COMMAND)) {
                System.out.println("Goodbye! Bobby signing out...");
                System.out.println(LINE);
                break;
            }

            if (command.equals(LIST_COMMAND)) {
                System.out.println("Here are the tasks in your list:");
                for (int i = 0; i < taskCount; i++) {
                    System.out.println((i + 1) + "." + tasks[i]);
                }
            } else if (command.startsWith(MARK_COMMAND + " ")) {
                int taskIndex = getTaskIndex(command, MARK_COMMAND);
                if (isValidTaskIndex(taskIndex, taskCount)) {
                    tasks[taskIndex].markAsDone();
                    System.out.println("Nice! I've marked this task as done:");
                    System.out.println("  " + tasks[taskIndex]);
                } else {
                    System.out.println("I couldn't find that task number.");
                }
            } else if (command.startsWith(UNMARK_COMMAND + " ")) {
                int taskIndex = getTaskIndex(command, UNMARK_COMMAND);
                if (isValidTaskIndex(taskIndex, taskCount)) {
                    tasks[taskIndex].markAsNotDone();
                    System.out.println("OK, I've marked this task as not done yet:");
                    System.out.println("  " + tasks[taskIndex]);
                } else {
                    System.out.println("I couldn't find that task number.");
                }
            } else if (command.startsWith(TODO_COMMAND + " ")) {
                Task task = new Todo(command.substring(TODO_COMMAND.length()).trim());
                tasks[taskCount] = task;
                taskCount++;
                printTaskAdded(task, taskCount);
            } else if (command.startsWith(DEADLINE_COMMAND + " ")) {
                int byIndex = command.indexOf(BY_SEPARATOR);
                if (byIndex == -1) {
                    System.out.println("Please tell me the deadline using /by.");
                } else {
                    String description = command.substring(DEADLINE_COMMAND.length(), byIndex).trim();
                    String by = command.substring(byIndex + BY_SEPARATOR.length()).trim();
                    Task task = new Deadline(description, by);
                    tasks[taskCount] = task;
                    taskCount++;
                    printTaskAdded(task, taskCount);
                }
            } else if (command.startsWith(EVENT_COMMAND + " ")) {
                int fromIndex = command.indexOf(FROM_SEPARATOR);
                int toIndex = command.indexOf(TO_SEPARATOR);
                if (fromIndex == -1 || toIndex == -1 || toIndex < fromIndex) {
                    System.out.println("Please tell me the event time using /from and /to.");
                } else {
                    String description = command.substring(EVENT_COMMAND.length(), fromIndex).trim();
                    String from = command.substring(fromIndex + FROM_SEPARATOR.length(), toIndex).trim();
                    String to = command.substring(toIndex + TO_SEPARATOR.length()).trim();
                    Task task = new Event(description, from, to);
                    tasks[taskCount] = task;
                    taskCount++;
                    printTaskAdded(task, taskCount);
                }
            } else {
                System.out.println("Please start a task with todo, deadline, or event.");
            }

            System.out.println(LINE);
        }
    }

    private static int getTaskIndex(String command, String commandWord) {
        String taskNumber = command.substring(commandWord.length()).trim();
        try {
            return Integer.parseInt(taskNumber) - 1;
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private static boolean isValidTaskIndex(int taskIndex, int taskCount) {
        return taskIndex >= 0 && taskIndex < taskCount;
    }

    private static void printTaskAdded(Task task, int taskCount) {
        System.out.println("Got it. I've added this task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + taskCount + " tasks in the list.");
    }
}
