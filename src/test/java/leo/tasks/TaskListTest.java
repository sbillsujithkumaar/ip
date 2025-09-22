package leo.tasks;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import leo.LeoException;

/**
 * Essential test suite for the TaskList class.
 * Focuses on testing the find method and other critical functionality.
 */
public class TaskListTest {

    private TaskList taskList;
    private ToDo todo1;
    private ToDo todo2;
    private Deadline deadline1;
    private Event event1;

    @BeforeEach
    public void setUp() {
        taskList = new TaskList();
        
        // Create test tasks
        todo1 = new ToDo("read book");
        todo2 = new ToDo("write essay");
        deadline1 = new Deadline("submit assignment", LocalDate.of(2024, 12, 31));
        event1 = new Event("team meeting", "2pm", "3pm");
        
        // Add tasks to list
        taskList.add(todo1);
        taskList.add(todo2);
        taskList.add(deadline1);
        taskList.add(event1);
    }

    // Test find method - core functionality
    /**
     * Tests that find method returns exact matches correctly.
     */
    @Test
    public void find_exactMatch_returnsMatchingTasks() {
        List<Task> results = taskList.find("book");
        assertEquals(1, results.size());
        assertSame(todo1, results.get(0));
    }

    /**
     * Tests that find method returns partial matches correctly.
     */
    @Test
    public void find_partialMatch_returnsMatchingTasks() {
        List<Task> results = taskList.find("read");
        assertEquals(1, results.size());
        assertSame(todo1, results.get(0));
    }

    /**
     * Tests that find method is case-insensitive.
     */
    @Test
    public void find_caseInsensitive_returnsMatchingTasks() {
        List<Task> results = taskList.find("BOOK");
        assertEquals(1, results.size());
        assertSame(todo1, results.get(0));
    }

    /**
     * Tests that find method returns all matching tasks when multiple matches exist.
     */
    @Test
    public void find_multipleMatches_returnsAllMatchingTasks() {
        List<Task> results = taskList.find("e");
        assertEquals(4, results.size()); // All tasks contain 'e'
        assertTrue(results.contains(todo1));
        assertTrue(results.contains(todo2));
        assertTrue(results.contains(deadline1));
        assertTrue(results.contains(event1));
    }

    /**
     * Tests that find method returns empty list when no matches found.
     */
    @Test
    public void find_noMatches_returnsEmptyList() {
        List<Task> results = taskList.find("xyz");
        assertTrue(results.isEmpty());
    }

    /**
     * Tests that find method with empty keyword returns all tasks.
     */
    @Test
    public void find_emptyKeyword_returnsAllTasks() {
        List<Task> results = taskList.find("");
        assertEquals(4, results.size()); // Empty string matches all tasks
        assertTrue(results.contains(todo1));
        assertTrue(results.contains(todo2));
        assertTrue(results.contains(deadline1));
        assertTrue(results.contains(event1));
    }

    /**
     * Tests that find method works with deadline task descriptions.
     */
    @Test
    public void find_deadlineDescription_returnsMatchingTasks() {
        List<Task> results = taskList.find("assignment");
        assertEquals(1, results.size());
        assertSame(deadline1, results.get(0));
    }

    /**
     * Tests that find method works with event task descriptions.
     */
    @Test
    public void find_eventDescription_returnsMatchingTasks() {
        List<Task> results = taskList.find("meeting");
        assertEquals(1, results.size());
        assertSame(event1, results.get(0));
    }

    /**
     * Tests that find method returns empty list when task list is empty.
     */
    @Test
    public void find_emptyTaskList_returnsEmptyList() {
        TaskList emptyList = new TaskList();
        List<Task> results = emptyList.find("anything");
        assertTrue(results.isEmpty());
    }

    // Test other essential TaskList methods
    /**
     * Tests that adding a task increases the list size.
     */
    @Test
    public void add_taskIncreasesSize() {
        int initialSize = taskList.size();
        ToDo newTask = new ToDo("new task");
        taskList.add(newTask);
        assertEquals(initialSize + 1, taskList.size());
    }

    /**
     * Tests that getting task at valid index returns correct task.
     */
    @Test
    public void get_validIndex_returnsCorrectTask() {
        Task retrievedTask = taskList.get(0);
        assertSame(todo1, retrievedTask);
    }

    /**
     * Tests that getting task at invalid index throws AssertionError.
     */
    @Test
    public void get_invalidIndex_throwsException() {
        assertThrows(AssertionError.class, () -> taskList.get(-1));
        assertThrows(AssertionError.class, () -> taskList.get(100));
    }

    /**
     * Tests that removing task at valid index returns removed task and decreases size.
     */
    @Test
    public void remove_validIndex_returnsRemovedTask() {
        Task removedTask = taskList.remove(0);
        assertSame(todo1, removedTask);
        assertEquals(3, taskList.size());
    }

    /**
     * Tests that validating invalid index throws LeoException.
     */
    @Test
    public void validateIndex_invalidIndex_throwsException() {
        assertThrows(LeoException.class, () -> taskList.validateIndex(-1));
        assertThrows(LeoException.class, () -> taskList.validateIndex(4));
    }
}
