package leo.commands;

import leo.Ui;
import leo.storage.Storage;
import leo.tasks.TaskList;
import leo.LeoException;

import java.io.IOException;

public class MarkCommand extends Command {
    private final int index;
    public MarkCommand(int index) {
        this.index = index;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws LeoException {
        try {
            tasks.get(index).markAsDone();
            ui.showMarked(tasks.get(index));
            try {
                storage.save(tasks.list());
            } catch (IOException e) {
                ui.showError("Could not save: " + e.getMessage());
            }
        } catch (IndexOutOfBoundsException e) {
            throw new LeoException("Check your range!");
        }
    }
}
