package leo.tasks;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Represents a task with a specific deadline date.
 */
public class Deadline extends Task {
    private static final DateTimeFormatter OUTPUT = DateTimeFormatter.ofPattern("MMM d yyyy");
    protected LocalDate by;

    /**
     * Creates a new deadline task with the specified description and due date.
     *
     * @param description the task description
     * @param by the deadline date
     */
    public Deadline(String description, LocalDate by) {
        super(description);
        this.by = by;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by.format(OUTPUT) + ")";
    }

    /**
     * Returns the deadline date for this task.
     *
     * @return the deadline date
     */
    public LocalDate getBy() {
        return by;
    }
}
