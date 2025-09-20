package leo;

import leo.commands.Command;
import leo.storage.Storage;
import leo.tasks.TaskList;

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

    /**
     * Generates a response for the user's chat message.
     */
    public String getResponse(String input) {
        return "Duke heard: " + input;
    }

}
