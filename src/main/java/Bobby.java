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
            } else {
                tasks[taskCount] = new Task(command);
                taskCount++;
                System.out.println("added: " + command);
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
}
