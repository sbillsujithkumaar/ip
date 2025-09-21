package leo.tasks;

import leo.LeoException;

import java.util.ArrayList;
import java.util.List;

public class TaskList {
    private final List<Task> tasks = new ArrayList<>();

    public TaskList() {
    }

    public TaskList(List<Task> initial) {
        if (initial != null) {
            tasks.addAll(initial);
        }
    }

    /**
     * Returns the size of the current list of tasks.
     */
    public int size() {
        return tasks.size();
    }

    /**
     * Returns the task at {@code index}.
     *
     * @param index index of the task to retrieve.
     */
    public Task get(int index) {
        return tasks.get(index);
    }

    /**
     * Adds a task to the end of the list.
     *
     * @param task task to add.
     */
    public void add(Task task) {
        tasks.add(task);
    }

    /**
     * Removes and returns the task at {@code index}.
     *
     * @param index index of the task to remove.
     */
    public Task remove(int index) {
        return tasks.remove(index);
    }

    /**
     * Returns the current list of tasks.
     */
    public List<Task> list() {
        return tasks;
    }

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

