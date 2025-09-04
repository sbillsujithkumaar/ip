import java.io.IOException;

public class UnmarkCommand extends Command {
    private final int index;
    public UnmarkCommand(int index) {
        this.index = index;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws LeoException {
        try {
            tasks.get(index).markAsNotDone();
            ui.showUnmarked(tasks.get(index));
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
