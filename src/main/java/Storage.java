import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles loading and saving Bobby's tasks on the hard disk.
 */
public class Storage {
    private static final Path DATA_FILE = Path.of("data", "bobby.txt");
    private static final String SEPARATOR = " \\| ";

    /**
     * Loads saved tasks from the data file.
     *
     * @return tasks saved from an earlier run, or an empty list if no data file exists
     * @throws BobbyException if the data file exists but cannot be read or parsed
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
     * @param tasks current task list
     * @throws BobbyException if the tasks cannot be saved
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

    private static Task createTask(String[] parts) throws BobbyException {
        return switch (parts[0]) {
        case "T" -> createTodo(parts);
        case "D" -> createDeadline(parts);
        case "E" -> createEvent(parts);
        default -> throw new BobbyException("The saved task file contains an invalid task type.");
        };
    }

    private static Todo createTodo(String[] parts) throws BobbyException {
        if (parts.length != 3) {
            throw new BobbyException("The saved task file contains an invalid todo.");
        }
        return new Todo(parts[2]);
    }

    private static Deadline createDeadline(String[] parts) throws BobbyException {
        if (parts.length != 4) {
            throw new BobbyException("The saved task file contains an invalid deadline.");
        }
        return new Deadline(parts[2], DateTimeParser.parse(parts[3]));
    }

    private static Event createEvent(String[] parts) throws BobbyException {
        if (parts.length != 5) {
            throw new BobbyException("The saved task file contains an invalid event.");
        }
        return new Event(parts[2], DateTimeParser.parse(parts[3]), DateTimeParser.parse(parts[4]));
    }
}
