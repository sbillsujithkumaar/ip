import java.io.IOException;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Storage {
    private final Path filePath;

    // Constructor
    public Storage(String filePath) {
        this.filePath = Paths.get(filePath);
    }

    // Load from files
    public List<Task> load() throws IOException {

        List<Task> tasksList = new ArrayList<>();

        Path parent = filePath.getParent();
        if (parent != null && !Files.exists(parent)) {
            Files.createDirectories(parent);
        }

        // If file doesn't exist, create it and return empty tasksList
        if (!Files.exists(filePath)) {
            Files.createFile(filePath);
            return tasksList;
        }

        // File exists. Read file as string, format it and then save it
        Scanner sc = new Scanner(filePath);
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            Task t = formatLine(line);

            if (t != null) {
                tasksList.add(t);
            }
        }
        sc.close();

        return tasksList;
    }

    // String format: T/D/E  + 1/0 + description + dates
    private Task formatLine(String line) {
        // split by "|"
        String[] parts = line.split("\\|");
        if (parts.length < 3) {
            return null;
        }

        // Account for additional spaces
        for (int i = 0; i < parts.length; i++) {
            parts[i] = parts[i].trim();
        }

        // T/D/E
        String type = parts[0];
        // 1 - Done/ 0 - not Done
        boolean isDone = "1".equals(parts[1]);
        // description
        String desc = parts[2];

        Task t;
        switch (type) {
            case "T":
                t = new ToDo(desc);
                break;
            case "D":
                if (parts.length < 4) {
                    return null;
                }
                LocalDate byDate = LocalDate.parse(parts[3], DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                t = new Deadline(desc, byDate);
                break;
            case "E":
                if (parts.length < 5) {
                    return null;
                }
                t = new Event(desc, parts[3], parts[4]);
                break;
            default:
                return null;
        }

        if (isDone) {
            t.markAsDone();
        } else {
            t.markAsNotDone();
        }

        return t;
    }

    public void save(List<Task> tasksList) throws IOException {
        Path parent = filePath.getParent();
        if (parent != null && !Files.exists(parent)) {
            Files.createDirectories(parent);
        }

        // If file doesn't exist, create it
        if (!Files.exists(filePath)) {
            Files.createFile(filePath);
        }

        // Open file for writing
        FileWriter fw = new FileWriter(filePath.toFile());

        for (Task t : tasksList) {
            String status = t.isDone() ? "1" : "0";
            String line;

            if (t instanceof ToDo) {
                // T | status | description
                line = "T | " + status + " | " + t.getDescription();

            } else if (t instanceof Deadline) {
                // D | status | description | by
                String by = ((Deadline) t).getBy().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                line = "D | " + status + " | " + t.getDescription() + " | " + by;
            } else if (t instanceof Event) {
                // E | status | description | from | to
                String from = ((Event) t).getFrom();
                String to = ((Event) t).getTo();
                line = "E | " + status + " | " + t.getDescription() + " | " + from + " | " + to;
            } else {
                // Unknown type
                continue;
            }

            fw.write(line + System.lineSeparator());
        }

        fw.close();

    }
}

