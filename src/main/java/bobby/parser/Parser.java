package bobby.parser;

import bobby.BobbyException;
import bobby.task.Deadline;
import bobby.task.Event;
import bobby.task.Task;
import bobby.task.TaskList;
import bobby.task.Todo;
import bobby.util.DateTimeParser;

/**
 * Parses user commands into task operations or task objects.
 */
public class Parser {
    private static final String MARK_COMMAND = "mark";
    private static final String UNMARK_COMMAND = "unmark";
    private static final String DELETE_COMMAND = "delete";
    private static final String TODO_COMMAND = "todo";
    private static final String DEADLINE_COMMAND = "deadline";
    private static final String EVENT_COMMAND = "event";
    private static final String BY_SEPARATOR = " /by";
    private static final String FROM_SEPARATOR = " /from";
    private static final String TO_SEPARATOR = " /to";

    /**
     * Returns whether the input is the command word or starts with the command word followed by a space.
     *
     * @param command user input.
     * @param commandWord command word to test.
     * @return true if the command matches.
     */
    public static boolean isCommand(String command, String commandWord) {
        return command.equals(commandWord) || command.startsWith(commandWord + " ");
    }

    /**
     * Returns whether the input is a mark command.
     *
     * @param command user input.
     * @return true if this is a mark command.
     */
    public static boolean isMark(String command) {
        return isCommand(command, MARK_COMMAND);
    }

    /**
     * Returns whether the input is an unmark command.
     *
     * @param command user input.
     * @return true if this is an unmark command.
     */
    public static boolean isUnmark(String command) {
        return isCommand(command, UNMARK_COMMAND);
    }

    /**
     * Returns whether the input is a delete command.
     *
     * @param command user input.
     * @return true if this is a delete command.
     */
    public static boolean isDelete(String command) {
        return isCommand(command, DELETE_COMMAND);
    }

    /**
     * Parses the one-based task number in a command into a zero-based index.
     *
     * @param command full user command.
     * @param commandWord command word before the task number.
     * @param taskList current task list.
     * @return zero-based task index.
     * @throws BobbyException if the task number is missing, invalid, or out of range.
     */
    public static int parseTaskIndex(String command, String commandWord, TaskList taskList) throws BobbyException {
        String taskNumber = command.substring(commandWord.length()).trim();
        if (taskNumber.isEmpty()) {
            throw new BobbyException("Please provide a task number after " + commandWord + ".");
        }
        try {
            int taskIndex = Integer.parseInt(taskNumber) - 1;
            if (!taskList.isValidIndex(taskIndex)) {
                throw new BobbyException("I couldn't find that task number.");
            }
            return taskIndex;
        } catch (NumberFormatException e) {
            throw new BobbyException("Task numbers should be whole numbers.");
        }
    }

    /**
     * Parses a task-creating command.
     *
     * @param command user input.
     * @return task represented by the command.
     * @throws BobbyException if the command is invalid or unknown.
     */
    public static Task parseTask(String command) throws BobbyException {
        if (isCommand(command, TODO_COMMAND)) {
            return new Todo(getDescription(command, TODO_COMMAND, "todo"));
        } else if (isCommand(command, DEADLINE_COMMAND)) {
            return parseDeadline(command);
        } else if (isCommand(command, EVENT_COMMAND)) {
            return parseEvent(command);
        } else {
            throw new BobbyException("I don't know what that means yet.");
        }
    }

    /**
     * Parses a deadline command after confirming that it starts with the deadline command word.
     *
     * @param command full deadline command.
     * @return deadline task represented by the command.
     * @throws BobbyException if the description or /by field is missing or invalid.
     */
    private static Deadline parseDeadline(String command) throws BobbyException {
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
        return new Deadline(description, DateTimeParser.parse(by));
    }

    /**
     * Parses an event command after confirming that it starts with the event command word.
     *
     * @param command full event command.
     * @return event task represented by the command.
     * @throws BobbyException if the description, /from field, or /to field is missing or invalid.
     */
    private static Event parseEvent(String command) throws BobbyException {
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
        return new Event(description, DateTimeParser.parse(from), DateTimeParser.parse(to));
    }

    /**
     * Extracts and validates the description after a task command word.
     *
     * @param command full task command.
     * @param commandWord command word before the description.
     * @param taskType task type name used in the error message.
     * @return non-empty task description.
     * @throws BobbyException if the description is empty.
     */
    private static String getDescription(String command, String commandWord, String taskType) throws BobbyException {
        String description = command.substring(commandWord.length()).trim();
        if (description.isEmpty()) {
            throw new BobbyException("The description of a " + taskType + " cannot be empty.");
        }
        return description;
    }
}
