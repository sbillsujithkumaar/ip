public abstract class Command {
    public abstract void execute(TaskList tasks, Ui ui, Storage storage) throws LeoException;

    // Set as false at the start for all children
    public boolean isExit() {
        return false;
    }
}