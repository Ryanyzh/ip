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
    public static void saveTasks(ArrayList<Task> tasks) throws BobbyException {
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
        if (parts.length < 3) {
            throw new BobbyException("The saved task file contains an invalid task.");
        }

        Task task = createTask(parts);
        if (parts[1].equals("1")) {
            task.markAsDone();
        } else if (!parts[1].equals("0")) {
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
        return switch (parts[0]) {
        case "T" -> createTodo(parts);
        case "D" -> createDeadline(parts);
        case "E" -> createEvent(parts);
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
        if (parts.length != 3) {
            throw new BobbyException("The saved task file contains an invalid todo.");
        }
        return new Todo(parts[2]);
    }

    /**
     * Creates a deadline from split save-file fields.
     *
     * @param parts split deadline fields.
     * @return deadline represented by the fields.
     * @throws BobbyException if the deadline field count or date is invalid.
     */
    private static Deadline createDeadline(String[] parts) throws BobbyException {
        if (parts.length != 4) {
            throw new BobbyException("The saved task file contains an invalid deadline.");
        }
        return new Deadline(parts[2], DateTimeParser.parse(parts[3]));
    }

    /**
     * Creates an event from split save-file fields.
     *
     * @param parts split event fields.
     * @return event represented by the fields.
     * @throws BobbyException if the event field count or date fields are invalid.
     */
    private static Event createEvent(String[] parts) throws BobbyException {
        if (parts.length != 5) {
            throw new BobbyException("The saved task file contains an invalid event.");
        }
        return new Event(parts[2], DateTimeParser.parse(parts[3]), DateTimeParser.parse(parts[4]));
    }
}
