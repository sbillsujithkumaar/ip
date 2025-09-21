package leo.commands;

import leo.LeoException;
import leo.Ui;
import leo.storage.Storage;
import leo.tasks.Task;
import leo.tasks.TaskList;

/**
 * Command that marks a task as not done and saves the list.
 */
public class UnmarkCommand extends Command {
    private final int index;

    public UnmarkCommand(int index) {
        this.index = index;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws LeoException {
        // Guard clause - validate index early
        tasks.validateIndex(index);
        
        // Happy path - mark task as not done
        Task task = tasks.get(index);
        task.markAsNotDone();
        ui.showUnmarked(task);
        
        // Save changes
        saveTasksToStorage(tasks, ui, storage);
    }

}
