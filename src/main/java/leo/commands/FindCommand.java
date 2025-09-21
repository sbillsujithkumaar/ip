package leo.commands;

import leo.LeoException;
import leo.Ui;
import leo.storage.Storage;
import leo.tasks.Task;
import leo.tasks.TaskList;

import java.util.List;

/**
 * Command that searches tasks by keyword and shows matches.
 */
public class FindCommand extends Command {
    private final String keyword;

    public FindCommand(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws LeoException {
        if (keyword.trim().isEmpty()) {
            throw new LeoException("Find what? Provide a keyword!");
        }
        List<Task> matches = tasks.find(keyword.trim());
        ui.showFindResults(matches);
    }
}
