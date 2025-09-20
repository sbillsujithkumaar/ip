package leo.tasks;

public class Task {
    protected String description;
    protected boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public String getStatusIcon() {
        return (isDone ? "X" : " "); // mark done task with X
    }

    public void markAsDone() {
        this.isDone = true;
    }

    public void markAsNotDone() {
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
