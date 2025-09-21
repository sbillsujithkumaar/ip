package leo.tasks;

import java.util.ArrayList;
import java.util.List;

public class TaskList {
    // Start with empty ArrayList
    private final List<Task> tasks = new ArrayList<>();

    // Empty task list initialisation
    public TaskList() {
    }

    // Loaded task list initialisation (Populate)
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
        assert index >= 0 && index < tasks.size() : "index out of bounds";
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
        assert index >= 0 && index < tasks.size() : "index out of bounds";
        return tasks.remove(index);
    }

    /**
     * Returns the current list of tasks.
     */
    public List<Task> list() {
        return tasks;
    }

    public List<Task> find(String keyword) {
        String formattedKeyword = keyword.toLowerCase();
        List<Task> result = new ArrayList<>();
        for (Task t : tasks) {
            if (t.getDescription().toLowerCase().contains(formattedKeyword)) {
                result.add(t);
            }
        }
        return result;
    }
}
