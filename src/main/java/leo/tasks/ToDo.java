package leo.tasks;

/**
 * Represents a simple todo task without any specific deadline or time constraints.
 */
public class ToDo extends Task {
    /**
     * Creates a new todo task with the specified description.
     *
     * @param description the task description
     */
    public ToDo(String description) {
        super(description);
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
