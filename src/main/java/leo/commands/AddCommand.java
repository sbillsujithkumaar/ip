package leo.commands;


import leo.Ui;
import leo.storage.Storage;
import leo.tasks.Task;
import leo.tasks.TaskList;
import leo.LeoException;

public class AddCommand extends Command {
    private final Task task;

    public AddCommand(Task task) {
        this.task = task;
    }

    // Use Storage to save changes
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws LeoException {
        tasks.add(task);
        ui.showAdded(task, tasks.size());
        try {
            storage.save(tasks.list());
        } catch (Exception e) {
            ui.showError("Could not save: " + e.getMessage());
        }
    }
}
