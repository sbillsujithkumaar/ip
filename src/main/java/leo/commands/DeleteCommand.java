package leo.commands;

import leo.LeoException;
import leo.Ui;
import leo.storage.Storage;
import leo.tasks.Task;
import leo.tasks.TaskList;

/**
 * Command that deletes a task and saves the remaining list to storage.
 */
public class DeleteCommand extends Command {
    private final int index;

    public DeleteCommand(int index) {
        this.index = index;
    }

    // Use Storage to save changes
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws LeoException {
        // Guard clause - validate index early
        tasks.validateIndex(index);
        
        // Happy path - remove task
        Task removed = tasks.remove(index);
        ui.showRemoved(removed, tasks.size());
        
        // Save changes
        saveTasksToStorage(tasks, ui, storage);
    }

}
