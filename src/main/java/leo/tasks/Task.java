package leo.tasks;

/**
 * Represents a task with a description and completion status.
 */
public class Task {
    protected String description;
    protected boolean isDone;

    /**
     * Creates a new task with the specified description.
     *
     * @param description the task description
     */
    public Task(String description) {
        assert description != null && !description.trim().isEmpty() : "Task description must be non-empty";
        this.description = description;
        this.isDone = false;
    }

    /**
     * Returns the status icon for this task.
     *
     * @return "X" if done, " " if not done
     */
    public String getStatusIcon() {
        return (isDone ? "X" : " "); // mark done task with X
    }

    /**
     * Marks this task as done.
     */
    public void markAsDone() {
        assert !isDone : "Task being marked should not already be marked";
        this.isDone = true;
    }

    /**
     * Marks this task as not done.
     */
    public void markAsNotDone() {
        assert isDone : "Task being unmarked should not already be unmarked";
        this.isDone = false;
    }

    /**
     * Returns the description of this task.
     *
     * @return the task description
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Returns whether this task is done.
     *
     * @return true if the task is done, false otherwise
     */
    public boolean isDone() {
        return this.isDone;
    }

    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + description;
    }
}
