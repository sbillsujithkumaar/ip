package leo.commands;

import leo.Ui;
import leo.storage.Storage;
import leo.tasks.TaskList;

/**
 * Command that prints the help message.
 */
public class HelpCommand extends Command {
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ui.showHelp();
    }
}