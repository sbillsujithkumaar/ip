package leo.commands;

import leo.LeoException;
import leo.Ui;
import leo.storage.Storage;
import leo.tasks.TaskList;

import java.io.IOException;

/**
 * Base type for all user commands executed by Leo.
 */
public abstract class Command {
    /**
     * Executes the command.
     *
     * @param tasks   the current task list (model).
     * @param ui      the UI helper for output.
     * @param storage the storage facility for persistence.
     *
     * @throws LeoException if execution fails for a user-facing reason.
     */
    public abstract void execute(TaskList tasks, Ui ui, Storage storage) throws LeoException;

    /**
     * Determines when the program terminates
     *
     * @return false if program is still running. true if otherwise
     */
    public boolean isExit() {
        return false;
    }

    /**
     * Saves tasks to storage with error handling.
     * Centralized method to avoid duplication across command classes.
     *
     * @param tasks the task list to save
     * @param ui the UI helper for displaying errors
     * @param storage the storage facility
     */
    protected void saveTasksToStorage(TaskList tasks, Ui ui, Storage storage) {
        try {
            storage.save(tasks.list());
        } catch (IOException e) {
            ui.showError("Could not save: " + e.getMessage());
        }
    }
}
