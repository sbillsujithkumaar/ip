package leo.tasks;

public class Task {
    protected String description;
    protected boolean isDone;

    public Task(String description) {
        assert description != null && !description.trim().isEmpty() : "Task description must be non-empty";
        this.description = description;
        this.isDone = false;
    }

    public String getStatusIcon() {
        return (isDone ? "X" : " "); // mark done task with X
    }

    public void markAsDone() {
        assert !isDone : "Task being marked should not already be marked";
        this.isDone = true;
    }

    public void markAsNotDone() {
        assert isDone : "Task being unmarked should not already be unmarked";
        this.isDone = false;
    }

    public String getDescription() {
        return this.description;
    }

    // when it is true, save to disk
    public boolean isDone() {
        return this.isDone;
    }


    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + description;
    }
}
