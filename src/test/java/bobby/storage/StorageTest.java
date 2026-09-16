package bobby.storage;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import bobby.BobbyException;

/**
 * Tests saved-data loading behavior for missing and malformed task files.
 */
class StorageTest {
    private static final Path DATA_FILE = Path.of("data", "bobby.txt");

    @TempDir
    private Path temporaryFolder;

    private Path backupFile;
    private boolean hasOriginalDataFile;

    @BeforeEach
    void backUpDataFile() throws IOException {
        Files.createDirectories(DATA_FILE.getParent());
        hasOriginalDataFile = Files.exists(DATA_FILE);
        if (hasOriginalDataFile) {
            backupFile = temporaryFolder.resolve("bobby-backup.txt");
            Files.copy(DATA_FILE, backupFile, StandardCopyOption.REPLACE_EXISTING);
        }
    }

    @AfterEach
    void restoreDataFile() throws IOException {
        if (hasOriginalDataFile) {
            Files.copy(backupFile, DATA_FILE, StandardCopyOption.REPLACE_EXISTING);
        } else {
            Files.deleteIfExists(DATA_FILE);
        }
    }

    @Test
    void loadTasks_missingFile_returnsEmptyList() throws BobbyException, IOException {
        Files.deleteIfExists(DATA_FILE);

        assertTrue(Storage.loadTasks().isEmpty());
    }

    @Test
    void loadTasks_duplicateTasks_throwsBobbyException() throws IOException {
        writeData(
                "T | 0 | read book",
                "T | 1 | read book");

        assertThrows(BobbyException.class, Storage::loadTasks);
    }

    @Test
    void loadTasks_invalidDescription_throwsBobbyException() throws IOException {
        writeData("T | 0 | ");

        assertThrows(BobbyException.class, Storage::loadTasks);
    }

    @Test
    void loadTasks_invalidEventRange_throwsBobbyException() throws IOException {
        writeData("E | 0 | meeting | 2019-12-02T16:00 | 2019-12-02T14:00");

        assertThrows(BobbyException.class, Storage::loadTasks);
    }

    private void writeData(String... lines) throws IOException {
        Files.write(DATA_FILE, List.of(lines));
    }
}
