package leo.storage;

import leo.LeoException;
import leo.tasks.Deadline;
import leo.tasks.Event;
import leo.tasks.Task;
import leo.tasks.TaskType;
import leo.tasks.ToDo;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Loads and saves tasks to a plain-text storage file.
 */
public class Storage {
    // Task status codes
    private static final String TASK_DONE_STATUS = "1";
    private static final String TASK_NOT_DONE_STATUS = "0";
    
    // Storage format constants
    private static final String STORAGE_SEPARATOR = " | ";
    private static final String DATE_FORMAT_PATTERN = "yyyy-MM-dd";
    
    // Array indices for storage format
    private static final int TASK_TYPE_INDEX = 0;
    private static final int TASK_STATUS_INDEX = 1;
    private static final int TASK_DESC_INDEX = 2;
    private static final int DEADLINE_DATE_INDEX = 3;
    private static final int EVENT_FROM_INDEX = 3;
    private static final int EVENT_TO_INDEX = 4;
    
    // Minimum parts required for different task types
    private static final int MIN_TASK_PARTS = 3;
    private static final int MIN_DEADLINE_PARTS = 4;
    private static final int MIN_EVENT_PARTS = 5;
    
    private final Path filePath;

    /**
     * Creates a Storage instance for the specified file path.
     *
     * @param filePath the path to the storage file
     */
    public Storage(String filePath) {
        this.filePath = Paths.get(filePath);
    }

    /**
     * Loads tasks from disk. Also, creates files/directories if absent
     *
     * @return list of tasks found on disk. Return empty if none is found.
     * @throws LeoException if an I/O error occurs during loading the tasks.
     */
    public List<Task> load() throws LeoException {
        try {
            ensureDirectoryExists();
            
            // If file doesn't exist, create it and return empty list
            if (!Files.exists(filePath)) {
                Files.createFile(filePath);
                return new ArrayList<>();
            }

            return loadTasksFromFile();
        } catch (IOException e) {
            throw new LeoException("Storage error: " + e.getMessage());
        }
    }

    /**
     * Loads tasks from an existing file.
     * 
     * @return list of loaded tasks
     * @throws IOException if file reading fails
     */
    private List<Task> loadTasksFromFile() throws IOException {
        List<Task> tasksList = new ArrayList<>();
        
        try (Scanner scanner = new Scanner(filePath)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                Task task = formatLine(line);
                
                if (task != null) {
                    tasksList.add(task);
                }
            }
        }
        
        return tasksList;
    }

    // String format: T/D/E  + 1/0 + description + dates
    private Task formatLine(String line) {
        // Guard clauses - handle error cases early
        String[] parts = parseAndTrimLineParts(line);
        if (parts == null) {
            return null; // Invalid line format
        }

        TaskType type = parseTaskType(parts[TASK_TYPE_INDEX]);
        if (type == null) {
            return null; // Unknown task type
        }

        Task task = createTaskFromType(type, parts[TASK_DESC_INDEX], parts);
        if (task == null) {
            return null; // Invalid task format
        }

        // Happy path - process valid task
        boolean isDone = TASK_DONE_STATUS.equals(parts[TASK_STATUS_INDEX]);
        setTaskCompletionStatus(task, isDone);
        return task;
    }

    /**
     * Parses line into parts and trims whitespace.
     * 
     * @param line the input line to parse
     * @return trimmed parts array, or null if insufficient parts
     */
    private String[] parseAndTrimLineParts(String line) {
        String[] parts = line.split("\\|");
        if (parts.length < MIN_TASK_PARTS) {
            return null;
        }

        // Account for additional spaces
        for (int i = 0; i < parts.length; i++) {
            parts[i] = parts[i].trim();
        }

        return parts;
    }

    /**
     * Parses task type from storage code.
     * 
     * @param code the task type code
     * @return TaskType or null if invalid
     */
    private TaskType parseTaskType(String code) {
        try {
            return TaskType.fromCode(code);
        } catch (IllegalArgumentException e) {
            return null; // skip unknown lines safely
        }
    }

    /**
     * Creates a task instance based on the task type.
     * 
     * @param type the task type
     * @param description the task description
     * @param parts the parsed line parts
     * @return Task instance or null if invalid format
     */
    private Task createTaskFromType(TaskType type, String description, String[] parts) {
        switch (type) {
            case TODO:
                return new ToDo(description);
                
            case DEADLINE:
                return createDeadlineTask(description, parts);
                
            case EVENT:
                return createEventTask(description, parts);
                
            default:
                return null;
        }
    }

    /**
     * Creates a Deadline task from parsed parts.
     */
    private Task createDeadlineTask(String description, String[] parts) {
        if (parts.length < MIN_DEADLINE_PARTS) {
            return null;
        }
        
        try {
            String dateString = parts[DEADLINE_DATE_INDEX];
            LocalDate byDate = LocalDate.parse(dateString, DateTimeFormatter.ofPattern(DATE_FORMAT_PATTERN));
            return new Deadline(description, byDate);
        } catch (DateTimeParseException e) {
            return null; // invalid date format
        }
    }

    /**
     * Creates an Event task from parsed parts.
     */
    private Task createEventTask(String description, String[] parts) {
        if (parts.length < MIN_EVENT_PARTS) {
            return null;
        }
        
        String from = parts[EVENT_FROM_INDEX];
        String to = parts[EVENT_TO_INDEX];
        return new Event(description, from, to);
    }

    /**
     * Sets the completion status of a task.
     */
    private void setTaskCompletionStatus(Task task, boolean isDone) {
        if (isDone) {
            task.markAsDone();
        } else {
            task.markAsNotDone();
        }
    }

    /**
     * Saves all tasks to disk by overwriting the target file.
     *
     * @param tasksList the updates list of tasks to save
     *
     * @throws IOException if saving fails
     */
    public void save(List<Task> tasksList) throws IOException {
        ensureDirectoryExists();
        ensureFileExists();

        try (FileWriter fw = new FileWriter(filePath.toFile())) {
            for (Task task : tasksList) {
                String line = formatTaskForStorage(task);
                if (line != null) {
                    fw.write(line + System.lineSeparator());
                }
            }
        }
    }

    /**
     * Ensures the parent directory exists, creating it if necessary.
     */
    private void ensureDirectoryExists() throws IOException {
        Path parent = filePath.getParent();
        if (parent != null && !Files.exists(parent)) {
            Files.createDirectories(parent);
        }
    }

    /**
     * Ensures the storage file exists, creating it if necessary.
     */
    private void ensureFileExists() throws IOException {
        if (!Files.exists(filePath)) {
            Files.createFile(filePath);
        }
    }

    /**
     * Formats a task for storage in the appropriate format.
     * 
     * @param task the task to format
     * @return formatted string for storage, or null if task type is unknown
     */
    private String formatTaskForStorage(Task task) {
        String status = task.isDone() ? TASK_DONE_STATUS : TASK_NOT_DONE_STATUS;
        String baseFormat = STORAGE_SEPARATOR + status + STORAGE_SEPARATOR + task.getDescription();

        if (task instanceof ToDo) {
            return TaskType.TODO.code() + baseFormat;
        } else if (task instanceof Deadline) {
            String by = ((Deadline) task).getBy().format(DateTimeFormatter.ofPattern(DATE_FORMAT_PATTERN));
            return TaskType.DEADLINE.code() + baseFormat + STORAGE_SEPARATOR + by;
        } else if (task instanceof Event) {
            String from = ((Event) task).getFrom();
            String to = ((Event) task).getTo();
            return TaskType.EVENT.code() + baseFormat + STORAGE_SEPARATOR + from + STORAGE_SEPARATOR + to;
        } else {
            // Unknown task type - this should never happen in normal operation
            // but we handle it explicitly to satisfy the default branch requirement
            System.err.println("Warning: Unknown task type encountered: " + task.getClass().getSimpleName());
            return null;
        }
    }
}

