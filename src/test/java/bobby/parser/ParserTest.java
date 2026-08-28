package bobby.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import bobby.BobbyException;
import bobby.task.Deadline;
import bobby.task.Event;
import bobby.task.TaskList;
import bobby.task.Todo;

/**
 * Tests command parsing behavior that affects which task or task index Bobby uses.
 */
class ParserTest {
    @Test
    void isCommand_matchesExactCommandOrCommandFollowedBySpace_returnsTrue() {
        assertTrue(Parser.isCommand("todo", "todo"));
        assertTrue(Parser.isCommand("todo read book", "todo"));
    }

    @Test
    void isCommand_commandOnlyAppearsAsPrefix_returnsFalse() {
        assertFalse(Parser.isCommand("todoread book", "todo"));
        assertFalse(Parser.isCommand("deadline", "dead"));
    }

    @Test
    void isFind_findCommand_returnsTrue() {
        assertTrue(Parser.isFind("find"));
        assertTrue(Parser.isFind("find book"));
    }

    @Test
    void parseFindKeyword_validKeyword_returnsTrimmedKeyword() throws BobbyException {
        assertEquals("book", Parser.parseFindKeyword("find book"));
        assertEquals("library book", Parser.parseFindKeyword("find   library book  "));
    }

    @Test
    void parseFindKeyword_missingKeyword_throwsBobbyException() {
        assertThrows(BobbyException.class, () -> Parser.parseFindKeyword("find"));
        assertThrows(BobbyException.class, () -> Parser.parseFindKeyword("find   "));
    }

    @Test
    void parseTask_validTaskCommands_returnsCorrectTaskTypesAndDisplayText() throws BobbyException {
        assertInstanceOf(Todo.class, Parser.parseTask("todo borrow book"));

        Deadline deadline = assertInstanceOf(Deadline.class,
                Parser.parseTask("deadline return book /by 2019-12-02"));
        Event event = assertInstanceOf(Event.class,
                Parser.parseTask("event meeting /from 2019-12-02 1400 /to 2019-12-02 1600"));

        assertEquals("[D][ ] return book (by: Dec 2 2019)", deadline.toString());
        assertEquals("[E][ ] meeting (from: Dec 2 2019, 2:00pm to: Dec 2 2019, 4:00pm)",
                event.toString());
    }

    @Test
    void parseTask_invalidTaskCommands_throwBobbyException() {
        assertThrows(BobbyException.class, () -> Parser.parseTask("todo"));
        assertThrows(BobbyException.class, () -> Parser.parseTask("deadline return book"));
        assertThrows(BobbyException.class, () -> Parser.parseTask("deadline return book /by"));
        assertThrows(BobbyException.class, () -> Parser.parseTask("event meeting /from 2019-12-02 1400"));
        assertThrows(BobbyException.class,
                () -> Parser.parseTask("event meeting /from 2019-12-02 1400 /to"));
        assertThrows(BobbyException.class, () -> Parser.parseTask("blah"));
    }

    @Test
    void parseTaskIndex_validOneBasedTaskNumber_returnsZeroBasedIndex() throws BobbyException {
        TaskList taskList = new TaskList(new ArrayList<>());
        taskList.add(new Todo("first"));
        taskList.add(new Todo("second"));

        assertEquals(0, Parser.parseTaskIndex("mark 1", "mark", taskList));
        assertEquals(1, Parser.parseTaskIndex("delete 2", "delete", taskList));
    }

    @Test
    void parseTaskIndex_missingInvalidOrOutOfRangeTaskNumber_throwsBobbyException() {
        TaskList taskList = new TaskList(new ArrayList<>());
        taskList.add(new Todo("first"));

        assertThrows(BobbyException.class, () -> Parser.parseTaskIndex("mark", "mark", taskList));
        assertThrows(BobbyException.class, () -> Parser.parseTaskIndex("mark two", "mark", taskList));
        assertThrows(BobbyException.class, () -> Parser.parseTaskIndex("mark 0", "mark", taskList));
        assertThrows(BobbyException.class, () -> Parser.parseTaskIndex("mark 2", "mark", taskList));
    }
}
