package leo.commands;

import leo.LeoException;
import leo.Ui;
import leo.storage.Storage;
import leo.tasks.TaskList;

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
}
