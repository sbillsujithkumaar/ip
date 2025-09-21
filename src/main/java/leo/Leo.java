package leo;

import leo.commands.Command;
import leo.storage.Storage;
import leo.tasks.TaskList;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * Entry point and main application loop for Leo.
 */
public class Leo {
    private Storage storage;
    private TaskList tasks;
    private Ui ui;

    /**
     * Creates a Leo instance backed by the given save file path.
     *
     * @param filePath path to the storage file
     */
    public Leo(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        try {
            tasks = new TaskList(storage.load());
        } catch (LeoException e) {
            ui.showLoadingError();
            tasks = new TaskList();
        }
    }

    /**
     * Runs the entire program, from welcome to reading user input to exiting.
     */
    public void run() {
        ui.showWelcome();
        boolean isExit = false;
        while (!isExit) {
            try {
                String fullCommand = ui.readCommand();
                ui.showLine();
                Command c = Parser.parse(fullCommand);
                c.execute(tasks, ui, storage);
                isExit = c.isExit();
            } catch (LeoException e) {
                ui.showError(e.getMessage());
            } finally {
                ui.showLine();
            }
        }
    }

    public static void main(String[] args) {
        new Leo("data/tasks.txt").run();
    }

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
