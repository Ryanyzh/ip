import java.util.Scanner;

/**
 * Entry point for the Bobby chatbot application.
 */
public class Bobby {
    private static final String LINE = "____________________________________________________________";
    private static final String BYE_COMMAND = "bye";
    private static final String LIST_COMMAND = "list";
    private static final int MAX_TASKS = 100;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] tasks = new String[MAX_TASKS];
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
                for (int i = 0; i < taskCount; i++) {
                    System.out.println((i + 1) + ". " + tasks[i]);
                }
            } else {
                tasks[taskCount] = command;
                taskCount++;
                System.out.println("added: " + command);
            }

            System.out.println(LINE);
        }
    }
}
