package leo.commands;

import leo.LeoException;
import leo.Ui;
import leo.storage.Storage;
import leo.tasks.Task;
import leo.tasks.TaskList;

public class DeleteCommand extends Command {
    private final int index;

    public DeleteCommand(int index) {
        this.index = index;
    }

    // Use Storage to save changes
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws LeoException {
        try {
            Task removed = tasks.remove(index);

            ui.showRemoved(removed, tasks.size());
            try {
                storage.save(tasks.list());
            } catch (Exception e) {
                ui.showError("Could not save: " + e.getMessage());
            }
        } catch (IndexOutOfBoundsException e) {
            throw new LeoException("Index out of range.");
        }
    }
}