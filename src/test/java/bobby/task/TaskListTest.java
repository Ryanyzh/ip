package bobby.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

/**
 * Tests task-list operations that mutate Bobby's in-memory task collection.
 */
class TaskListTest {
    @Test
    void add_appendsTaskAndIncreasesSize() {
        TaskList taskList = new TaskList(new ArrayList<>());
        Todo task = new Todo("borrow book");

        taskList.add(task);

        assertEquals(1, taskList.size());
        assertSame(task, taskList.asList().get(0));
    }

    @Test
    void markAndUnmark_updatesSelectedTaskOnly() {
        TaskList taskList = new TaskList(new ArrayList<>());
        Todo firstTask = new Todo("first");
        Todo secondTask = new Todo("second");
        taskList.add(firstTask);
        taskList.add(secondTask);

        Task markedTask = taskList.mark(1);

        assertSame(secondTask, markedTask);
        assertEquals("[T][ ] first", firstTask.toString());
        assertEquals("[T][X] second", secondTask.toString());

        Task unmarkedTask = taskList.unmark(1);

        assertSame(secondTask, unmarkedTask);
        assertEquals("[T][ ] second", secondTask.toString());
    }

    @Test
    void delete_removesSelectedTaskAndKeepsRemainingOrder() {
        TaskList taskList = new TaskList(new ArrayList<>());
        Todo firstTask = new Todo("first");
        Todo secondTask = new Todo("second");
        Todo thirdTask = new Todo("third");
        taskList.add(firstTask);
        taskList.add(secondTask);
        taskList.add(thirdTask);

        Task deletedTask = taskList.delete(1);

        assertSame(secondTask, deletedTask);
        assertEquals(2, taskList.size());
        assertSame(firstTask, taskList.asList().get(0));
        assertSame(thirdTask, taskList.asList().get(1));
    }

    @Test
    void isValidIndex_checksLowerAndUpperBounds() {
        TaskList taskList = new TaskList(new ArrayList<>());
        taskList.add(new Todo("only task"));

        assertFalse(taskList.isValidIndex(-1));
        assertTrue(taskList.isValidIndex(0));
        assertFalse(taskList.isValidIndex(1));
    }
}
