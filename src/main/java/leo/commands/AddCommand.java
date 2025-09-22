package leo.commands;

import leo.LeoException;
import leo.Ui;
import leo.storage.Storage;
import leo.tasks.Task;
import leo.tasks.TaskList;

/**
 * Command that adds a task and saves the list to storage.
 */
public class AddCommand extends Command {
    private final Task task;

    public AddCommand(Task task) {
        this.task = task;
    }

    /**
     * Returns the task to be added.
     *
     * @return the task
     */
    public Task getTask() {
        return task;
    }

    // Use Storage to save changes
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws LeoException {
        tasks.add(task);
        ui.showAdded(task, tasks.size());
        saveTasksToStorage(tasks, ui, storage);
    }

}
