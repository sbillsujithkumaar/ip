package leo.commands;

import leo.Ui;
import leo.storage.Storage;
import leo.tasks.TaskList;

/**
 * Command that terminates the application.
 */
public class ExitCommand extends Command {
    // Storage, tasks not used: only display bye msg
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ui.showBye();
    }

    @Override
    public boolean isExit() {
        return true;
    }
}
