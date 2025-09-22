package leo.tasks;

import leo.LeoException;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a list of tasks with operations to manage them.
 */
public class TaskList {
    private final List<Task> tasks = new ArrayList<>();

    /**
     * Creates an empty task list.
     */
    public TaskList() {
    }

    /**
     * Creates a task list with the specified initial tasks.
     *
     * @param initial the initial list of tasks
     */
    public TaskList(List<Task> initial) {
        if (initial != null) {
            tasks.addAll(initial);
        }
    }

    /**
     * Returns the size of the current list of tasks.
     *
     * @return the number of tasks in the list
     */
    public int size() {
        return tasks.size();
    }

    /**
     * Returns the task at the specified index.
     *
     * @param index index of the task to retrieve
     * @return the task at the specified index
     */
    public Task get(int index) {
        assert index >= 0 && index < tasks.size() : "index out of bounds";
        return tasks.get(index);
    }

    /**
     * Adds a task to the end of the list.
     *
     * @param task task to add
     */
    public void add(Task task) {
        tasks.add(task);
    }

    /**
     * Removes and returns the task at the specified index.
     *
     * @param index index of the task to remove
     * @return the removed task
     */
    public Task remove(int index) {
        assert index >= 0 && index < tasks.size() : "index out of bounds";
        return tasks.remove(index);
    }

    /**
     * Returns the current list of tasks.
     *
     * @return the list of tasks
     */
    public List<Task> list() {
        return tasks;
    }

    /**
     * Finds all tasks that contain the specified keyword in their description.
     *
     * @param keyword the search keyword
     * @return a list of matching tasks
     */
    public List<Task> find(String keyword) {
        String searchKeyword = keyword.toLowerCase();
        List<Task> matchingTasks = new ArrayList<>();
        
        for (Task task : tasks) {
            if (taskMatchesKeyword(task, searchKeyword)) {
                matchingTasks.add(task);
            }
        }
        
        return matchingTasks;
    }
    
    /**
     * Checks if a task's description contains the search keyword.
     *
     * @param task the task to check
     * @param searchKeyword the keyword to search for
     * @return true if the task description contains the keyword
     */
    private boolean taskMatchesKeyword(Task task, String searchKeyword) {
        String taskDescription = task.getDescription().toLowerCase();
        return taskDescription.contains(searchKeyword);
    }

    /**
     * Validates that the given index is within the valid range for this task list.
     * Throws LeoException if the index is invalid.
     *
     * @param index the index to validate
     * @throws LeoException if the index is out of range
     */
    public void validateIndex(int index) throws LeoException {
        if (index < 0 || index >= tasks.size()) {
            throw new LeoException("Index out of range.");
        }
    }
}

