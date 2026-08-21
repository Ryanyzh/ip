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
    private static final String BY_SEPARATOR = " /by";
    private static final String FROM_SEPARATOR = " /from";
    private static final String TO_SEPARATOR = " /to";
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
            String command = scanner.nextLine().trim();
            System.out.println(LINE);

            if (command.equals(BYE_COMMAND)) {
                System.out.println("Goodbye! Bobby signing out...");
                System.out.println(LINE);
                break;
            }

            try {
                taskCount = handleCommand(command, tasks, taskCount);
            } catch (BobbyException e) {
                System.out.println("Bobby needs a clearer command: " + e.getMessage());
            }

            System.out.println(LINE);
        }
    }

    private static int handleCommand(String command, Task[] tasks, int taskCount) throws BobbyException {
        if (command.equals(LIST_COMMAND)) {
            printTaskList(tasks, taskCount);
            return taskCount;
        } else if (isCommand(command, MARK_COMMAND)) {
            markTask(command, MARK_COMMAND, tasks, taskCount, true);
            return taskCount;
        } else if (isCommand(command, UNMARK_COMMAND)) {
            markTask(command, UNMARK_COMMAND, tasks, taskCount, false);
            return taskCount;
        } else if (isCommand(command, TODO_COMMAND)) {
            Task task = new Todo(getDescription(command, TODO_COMMAND, "todo"));
            return addTask(task, tasks, taskCount);
        } else if (isCommand(command, DEADLINE_COMMAND)) {
            return addDeadline(command, tasks, taskCount);
        } else if (isCommand(command, EVENT_COMMAND)) {
            return addEvent(command, tasks, taskCount);
        } else {
            throw new BobbyException("I don't know what that means yet.");
        }
    }

    private static boolean isCommand(String command, String commandWord) {
        return command.equals(commandWord) || command.startsWith(commandWord + " ");
    }

    private static void printTaskList(Task[] tasks, int taskCount) {
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < taskCount; i++) {
            System.out.println((i + 1) + "." + tasks[i]);
        }
    }

    private static void markTask(String command, String commandWord, Task[] tasks, int taskCount, boolean isDone)
            throws BobbyException {
        int taskIndex = getTaskIndex(command, commandWord, taskCount);
        if (isDone) {
            tasks[taskIndex].markAsDone();
            System.out.println("Nice! I've marked this task as done:");
        } else {
            tasks[taskIndex].markAsNotDone();
            System.out.println("OK, I've marked this task as not done yet:");
        }
        System.out.println("  " + tasks[taskIndex]);
    }

    private static int addDeadline(String command, Task[] tasks, int taskCount) throws BobbyException {
        int byIndex = command.indexOf(BY_SEPARATOR);
        if (byIndex == -1) {
            throw new BobbyException("Please tell me the deadline using /by.");
        }
        String description = command.substring(DEADLINE_COMMAND.length(), byIndex).trim();
        String by = command.substring(byIndex + BY_SEPARATOR.length()).trim();
        if (description.isEmpty()) {
            throw new BobbyException("The description of a deadline cannot be empty.");
        }
        if (by.isEmpty()) {
            throw new BobbyException("The /by part of a deadline cannot be empty.");
        }
        return addTask(new Deadline(description, by), tasks, taskCount);
    }

    private static int addEvent(String command, Task[] tasks, int taskCount) throws BobbyException {
        int fromIndex = command.indexOf(FROM_SEPARATOR);
        int toIndex = command.indexOf(TO_SEPARATOR);
        if (fromIndex == -1 || toIndex == -1 || toIndex < fromIndex) {
            throw new BobbyException("Please tell me the event time using /from and /to.");
        }
        String description = command.substring(EVENT_COMMAND.length(), fromIndex).trim();
        String from = command.substring(fromIndex + FROM_SEPARATOR.length(), toIndex).trim();
        String to = command.substring(toIndex + TO_SEPARATOR.length()).trim();
        if (description.isEmpty()) {
            throw new BobbyException("The description of an event cannot be empty.");
        }
        if (from.isEmpty()) {
            throw new BobbyException("The /from part of an event cannot be empty.");
        }
        if (to.isEmpty()) {
            throw new BobbyException("The /to part of an event cannot be empty.");
        }
        return addTask(new Event(description, from, to), tasks, taskCount);
    }

    private static String getDescription(String command, String commandWord, String taskType) throws BobbyException {
        String description = command.substring(commandWord.length()).trim();
        if (description.isEmpty()) {
            throw new BobbyException("The description of a " + taskType + " cannot be empty.");
        }
        return description;
    }

    private static int addTask(Task task, Task[] tasks, int taskCount) throws BobbyException {
        if (taskCount == MAX_TASKS) {
            throw new BobbyException("The task list is full.");
        }
        tasks[taskCount] = task;
        taskCount++;
        printTaskAdded(task, taskCount);
        return taskCount;
    }

    private static int getTaskIndex(String command, String commandWord, int taskCount) throws BobbyException {
        String taskNumber = command.substring(commandWord.length()).trim();
        if (taskNumber.isEmpty()) {
            throw new BobbyException("Please provide a task number after " + commandWord + ".");
        }
        try {
            int taskIndex = Integer.parseInt(taskNumber) - 1;
            if (!isValidTaskIndex(taskIndex, taskCount)) {
                throw new BobbyException("I couldn't find that task number.");
            }
            return taskIndex;
        } catch (NumberFormatException e) {
            throw new BobbyException("Task numbers should be whole numbers.");
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
