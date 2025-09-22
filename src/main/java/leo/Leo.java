package leo;

import leo.commands.Command;
import leo.storage.Storage;
import leo.tasks.Task;
import leo.tasks.TaskList;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

/**
 * Entry point and main application loop for Leo.
 */
public class Leo {
    // Default file path constant
    private static final String DEFAULT_TASK_FILE_PATH = "data/tasks.txt";
    
    private Storage storage;
    private TaskList tasks;
    private Ui ui;

    /**
     * Creates a Leo instance backed by the given save file path.
     *
     * @param filePath path to the storage file
     */
    public Leo(String filePath) {
        // Initialize UI components
        this.ui = new Ui();
        this.storage = new Storage(filePath);
        
        // Load existing tasks or start with empty list
        try {
            List<Task> loadedTasks = storage.load();
            this.tasks = new TaskList(loadedTasks);
        } catch (LeoException e) {
            ui.showLoadingError();
            this.tasks = new TaskList();
        }
    }

    /**
     * Runs the entire program, from welcome to reading user input to exiting.
     */
    public void run() {
        ui.showWelcome();
        
        boolean shouldExit = false;
        while (!shouldExit) {
            try {
                String userCommand = ui.readCommand();
                ui.showLine();
                
                Command parsedCommand = Parser.parse(userCommand);
                parsedCommand.execute(tasks, ui, storage);
                
                shouldExit = parsedCommand.isExit();
            } catch (LeoException e) {
                ui.showError(e.getMessage());
            } finally {
                ui.showLine();
            }
        }
    }

    /**
     * Main entry point for the Leo application.
     *
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        new Leo(DEFAULT_TASK_FILE_PATH).run();
    }

    /**
     * Captures the output from a Runnable operation.
     *
     * @param r the runnable to execute
     * @return the captured output as a string
     */
    private String captureOutput(Runnable r) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream prev = System.out;
        try (PrintStream ps = new PrintStream(baos)) {
            System.setOut(ps);
            r.run();
        } finally {
            System.setOut(prev);
        }
        return baos.toString().trim();
    }

    /**
     * Returns the same text the CLI would print at startup.
     */
    public String getWelcomeMessage() {
        return captureOutput(() -> ui.showWelcome());
    }

    /**
     * Existing getResponse(...) that parses+executes and returns printed text
     */
    public String getResponse(String input) {
        return captureOutput(() -> {
            try {
                Command cmd = Parser.parse(input);
                cmd.execute(tasks, ui, storage);
            } catch (LeoException e) {
                ui.showError(e.getMessage());
            }
        });
    }
}
