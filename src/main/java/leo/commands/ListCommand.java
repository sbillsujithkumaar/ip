package leo.commands;

import leo.Ui;
import leo.storage.Storage;
import leo.tasks.TaskList;

/**
 * Command that lists all current tasks.
 */
public class ListCommand extends Command {
    // Storage not used: only reads, does not save
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ui.showList(tasks);
    }
}
