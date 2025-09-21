package leo.commands;

import leo.LeoException;
import leo.Ui;
import leo.storage.Storage;
import leo.tasks.Task;
import leo.tasks.TaskList;

/**
 * Command that marks a task as done and saves the list.
 */
public class MarkCommand extends Command {
    private final int index;

    public MarkCommand(int index) {
        this.index = index;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws LeoException {
        // Guard clause - validate index early
        tasks.validateIndex(index);
        
        // Happy path - mark task as done
        Task task = tasks.get(index);
        task.markAsDone();
        ui.showMarked(task);
        
        // Save changes
        saveTasksToStorage(tasks, ui, storage);
    }

}
