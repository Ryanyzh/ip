package bobby;

import bobby.parser.Parser;
import bobby.storage.Storage;
import bobby.task.Task;
import bobby.task.TaskList;
import bobby.ui.Ui;

/**
 * Entry point for the Bobby chatbot application.
 */
public class Bobby {
    private static final String BYE_COMMAND = "bye";
    private static final String LIST_COMMAND = "list";
    private static final String MARK_COMMAND = "mark";
    private static final String UNMARK_COMMAND = "unmark";
    private static final String DELETE_COMMAND = "delete";

    /**
     * Starts Bobby, loads saved tasks, and handles user commands until the user exits.
     *
     * @param args command-line arguments, currently unused.
     */
    public static void main(String[] args) {
        Ui ui = new Ui();
        TaskList tasks = loadTasks(ui);
        ui.showWelcome();

        while (ui.hasNextCommand()) {
            String command = ui.readCommand();
            ui.showLine();

            if (command.equals(BYE_COMMAND)) {
                ui.showGoodbye();
                ui.showLine();
                break;
            }

            try {
                handleCommand(command, tasks, ui);
            } catch (BobbyException e) {
                ui.showCommandError(e.getMessage());
            }

            ui.showLine();
        }
    }

    /**
     * Loads tasks from storage and falls back to an empty task list if loading fails.
     *
     * @param ui UI used to show any loading error.
     * @return loaded task list, or an empty list if loading fails.
     */
    private static TaskList loadTasks(Ui ui) {
        try {
            return new TaskList(Storage.loadTasks());
        } catch (BobbyException e) {
            ui.showLoadingError(e.getMessage());
            return new TaskList(new java.util.ArrayList<>());
        }
    }

    /**
     * Handles one non-exit command by updating tasks, saving changes, and showing feedback.
     *
     * @param command command entered by the user.
     * @param tasks current task list.
     * @param ui UI used to show command results.
     * @throws BobbyException if the command is invalid.
     */
    private static void handleCommand(String command, TaskList tasks, Ui ui) throws BobbyException {
        if (command.equals(LIST_COMMAND)) {
            ui.showTaskList(tasks.asList());
        } else if (Parser.isMark(command)) {
            int taskIndex = Parser.parseTaskIndex(command, MARK_COMMAND, tasks);
            Task task = tasks.mark(taskIndex);
            Storage.saveTasks(tasks.asList());
            ui.showTaskMarked(task);
        } else if (Parser.isUnmark(command)) {
            int taskIndex = Parser.parseTaskIndex(command, UNMARK_COMMAND, tasks);
            Task task = tasks.unmark(taskIndex);
            Storage.saveTasks(tasks.asList());
            ui.showTaskUnmarked(task);
        } else if (Parser.isDelete(command)) {
            int taskIndex = Parser.parseTaskIndex(command, DELETE_COMMAND, tasks);
            Task removedTask = tasks.delete(taskIndex);
            Storage.saveTasks(tasks.asList());
            ui.showTaskDeleted(removedTask, tasks.size());
        } else {
            Task task = Parser.parseTask(command);
            tasks.add(task);
            Storage.saveTasks(tasks.asList());
            ui.showTaskAdded(task, tasks.size());
        }
    }
}
