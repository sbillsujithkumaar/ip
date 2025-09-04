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

    public int size() {
        return tasks.size();
    }

    public Task get(int index) {
        return tasks.get(index);
    }

    public void add(Task task) {
        tasks.add(task);
    }

    public Task remove(int index) {
        return tasks.remove(index);
    }

    public List<Task> list() {
        return tasks;
    }
}
