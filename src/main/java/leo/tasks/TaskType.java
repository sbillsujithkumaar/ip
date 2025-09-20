package leo.tasks;

/**
 * Type tag for tasks saved to disk.
 */
public enum TaskType {
    TODO("T"),
    DEADLINE("D"),
    EVENT("E");

    private final String code;

    /**
     * Constructs a {@code TaskType} with its storage code.
     *
     * @param code the single-letter code used in storage files
     */
    TaskType(String code) {
        this.code = code;
    }


    /**
     * Returns the single-letter storage code for this task type.
     *
     * @return the storage code (e.g., {@code "T"}, {@code "D"}, or {@code "E"})
     */
    public String code() {
        return code;
    }

    /**
     * Parses a storage code into its corresponding {@code TaskType}.
     *
     * @param code the single-letter storage code (must be {@code "T"}, {@code "D"}, or {@code "E"})
     *
     * @return the {@code TaskType} corresponding to the given code
     * @throws IllegalArgumentException if the code does not match any known task type
     */
    public static TaskType fromCode(String code) {
        switch (code) {
            case "T":
                return TODO;
            case "D":
                return DEADLINE;
            case "E":
                return EVENT;
            default:
                throw new IllegalArgumentException("Unknown task code: " + code);
        }
    }
}
