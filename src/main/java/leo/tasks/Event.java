package leo.tasks;

/**
 * Represents a task with a specific time range (from start to end).
 */
public class Event extends Task {
    protected String from;
    protected String to;

    /**
     * Creates a new event task with the specified description and time range.
     *
     * @param description the task description
     * @param from the start time/date
     * @param to the end time/date
     */
    public Event(String description, String from, String to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + from + " to: " + to + ")";
    }

    /**
     * Returns the start time/date for this event.
     *
     * @return the start time/date
     */
    public String getFrom() {
        return from;
    }

    /**
     * Returns the end time/date for this event.
     *
     * @return the end time/date
     */
    public String getTo() {
        return to;
    }
}
