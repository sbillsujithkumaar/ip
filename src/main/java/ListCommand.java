public class ListCommand extends Command {

    // Storage not used: only reads, does not save
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ui.showList(tasks);
    }
}