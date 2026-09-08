package bobby.storage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import bobby.BobbyException;
import bobby.task.Deadline;
import bobby.task.Event;
import bobby.task.Task;
import bobby.task.Todo;
import bobby.util.DateTimeParser;

/**
 * Handles loading and saving Bobby's tasks on the hard disk.
 */
public class Storage {
    private static final Path DATA_FILE = Path.of("data", "bobby.txt");
    private static final String SEPARATOR = " \\| ";
    private static final int TASK_TYPE_INDEX = 0;
    private static final int STATUS_INDEX = 1;
    private static final int DESCRIPTION_INDEX = 2;
    private static final int DEADLINE_BY_INDEX = 3;
    private static final int EVENT_FROM_INDEX = 3;
    private static final int EVENT_TO_INDEX = 4;
    private static final int MINIMUM_FIELD_COUNT = 3;
    private static final int TODO_FIELD_COUNT = 3;
    private static final int DEADLINE_FIELD_COUNT = 4;
    private static final int EVENT_FIELD_COUNT = 5;
    private static final String TODO_TYPE = "T";
    private static final String DEADLINE_TYPE = "D";
    private static final String EVENT_TYPE = "E";
    private static final String DONE_STATUS = "1";
    private static final String NOT_DONE_STATUS = "0";

    /**
     * Loads saved tasks from the data file.
     *
     * @return tasks saved from an earlier run, or an empty list if no data file exists.
     * @throws BobbyException if the data file exists but cannot be read or parsed.
     */
    public static ArrayList<Task> loadTasks() throws BobbyException {
        ArrayList<Task> tasks = new ArrayList<>();
        if (!Files.exists(DATA_FILE)) {
            return tasks;
        }

        try {
            List<String> lines = Files.readAllLines(DATA_FILE);
            for (String line : lines) {
                if (!line.isBlank()) {
                    tasks.add(parseTask(line));
                }
            }
        } catch (IOException e) {
            throw new BobbyException("I couldn't read the saved task file.");
        }
        return tasks;
    }

    /**
     * Saves all current tasks to the data file, creating the data folder if needed.
     *
     * @param tasks current task list.
     * @throws BobbyException if the tasks cannot be saved.
     */
    public static void saveTasks(List<Task> tasks) throws BobbyException {
        try {
            Files.createDirectories(DATA_FILE.getParent());
            ArrayList<String> lines = new ArrayList<>();
            for (Task task : tasks) {
                lines.add(task.toStorageString());
            }
            Files.write(DATA_FILE, lines);
        } catch (IOException e) {
            throw new BobbyException("I couldn't save the task list.");
        }
    }

    /**
     * Parses one line from the save file into a task and restores its done status.
     *
     * @param line one serialized task line.
     * @return task represented by the line.
     * @throws BobbyException if the line has an invalid structure, type, or status.
     */
    private static Task parseTask(String line) throws BobbyException {
        String[] parts = line.split(SEPARATOR, -1);
        if (parts.length < MINIMUM_FIELD_COUNT) {
            throw new BobbyException("The saved task file contains an invalid task.");
        }

        Task task = createTask(parts);
        if (parts[STATUS_INDEX].equals(DONE_STATUS)) {
            task.markAsDone();
        } else if (!parts[STATUS_INDEX].equals(NOT_DONE_STATUS)) {
            throw new BobbyException("The saved task file contains an invalid task status.");
        }
        return task;
    }

    /**
     * Creates the correct task subtype from split save-file fields.
     *
     * @param parts split task fields.
     * @return task represented by the fields.
     * @throws BobbyException if the task type is unknown or its fields are invalid.
     */
    private static Task createTask(String[] parts) throws BobbyException {
        return switch (parts[TASK_TYPE_INDEX]) {
            case TODO_TYPE -> createTodo(parts);
            case DEADLINE_TYPE -> createDeadline(parts);
            case EVENT_TYPE -> createEvent(parts);
            default -> throw new BobbyException("The saved task file contains an invalid task type.");
        };
    }

    /**
     * Creates a todo from split save-file fields.
     *
     * @param parts split todo fields.
     * @return todo represented by the fields.
     * @throws BobbyException if the todo field count is invalid.
     */
    private static Todo createTodo(String[] parts) throws BobbyException {
        if (parts.length != TODO_FIELD_COUNT) {
            throw new BobbyException("The saved task file contains an invalid todo.");
        }
        return new Todo(parts[DESCRIPTION_INDEX]);
    }

    /**
     * Creates a deadline from split save-file fields.
     *
     * @param parts split deadline fields.
     * @return deadline represented by the fields.
     * @throws BobbyException if the deadline field count or date is invalid.
     */
    private static Deadline createDeadline(String[] parts) throws BobbyException {
        if (parts.length != DEADLINE_FIELD_COUNT) {
            throw new BobbyException("The saved task file contains an invalid deadline.");
        }
        return new Deadline(parts[DESCRIPTION_INDEX], DateTimeParser.parse(parts[DEADLINE_BY_INDEX]));
    }

    /**
     * Creates an event from split save-file fields.
     *
     * @param parts split event fields.
     * @return event represented by the fields.
     * @throws BobbyException if the event field count or date fields are invalid.
     */
    private static Event createEvent(String[] parts) throws BobbyException {
        if (parts.length != EVENT_FIELD_COUNT) {
            throw new BobbyException("The saved task file contains an invalid event.");
        }
        return new Event(parts[DESCRIPTION_INDEX], DateTimeParser.parse(parts[EVENT_FROM_INDEX]),
                DateTimeParser.parse(parts[EVENT_TO_INDEX]));
    }
}
