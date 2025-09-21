package leo;

import leo.tasks.Task;
import leo.tasks.TaskList;

import java.util.List;
import java.util.Scanner;

/**
 * Console UI helpers for displaying output and reading input.
 */
public class Ui {

    private static final int HELP_COL_WIDTH = 32;
    private static final String HELP_LINE_FORMAT = "  %-" + HELP_COL_WIDTH + "s %s%n";

    // Create a scanner object
    private final Scanner scanner = new Scanner(System.in);

    /**
     * To display any errors
     *
     * @param msg the error message
     */
    public void showError(String msg) {
        System.out.println("__________________________________________________________________");
        System.out.println(" " + msg);
        System.out.println("__________________________________________________________________");
    }

    /**
     * reads user input using a single global scanner
     *
     * @return user input as a string
     */
    public String readCommand() {
        return scanner.nextLine();
    }

    /**
     * Prints a horizontal separator line.
     */
    public void showLine() {
        System.out.println("__________________________________________________________________");
    }

    /**
     * Shows an error shown when storage fails to load.
     */
    public void showLoadingError() {
        showError("I/O Error occurred when loading from memory");
    }

    /**
     * Prints the welcome logo and greeting.
     */
    public void showWelcome() {
        String logo = " ▄█          ▄████████  ▄██████▄\n"
            + "███         ███    ███ ███    ███\n"
            + "███         ███    █▀  ███    ███\n"
            + "███        ▄███▄▄▄     ███    ███\n"
            + "███       ▀▀███▀▀▀     ███    ███\n"
            + "███         ███    █▄  ███    ███\n"
            + "███▌    ▄   ███    ███ ███    ███\n"
            + "█████▄▄██   ██████████  ▀██████▀\n"
            + "▀\n";
        System.out.println(logo);

        showLine();
        System.out.println(" Hello! I'm Leo");
        System.out.println(" What can I do for you?");
        showLine();
    }

    /**
     * Prints the goodbye message.
     */
    public void showBye() {
        showLine();
        System.out.println(" Bye. Hope to see you again soon!");
        showLine();
    }

    /**
     * Only displays the message if a task is added
     *
     * @param task    new task that was added.
     * @param newSize new size of updated list of tasks.
     */
    public void showAdded(Task task, int newSize) {
        showLine();
        System.out.println(" Got it. I've added this task:");
        System.out.println("   " + task);
        System.out.println(" Now you have " + newSize + " tasks in the list.");
        showLine();
    }

    /**
     * Only displays the message if a task is removed
     *
     * @param task    new task that was removed.
     * @param newSize new size of updated list of tasks.
     */
    public void showRemoved(Task task, int newSize) {
        showLine();
        System.out.println(" Noted. I've removed this task:");
        System.out.println("   " + task);
        System.out.println(" Now you have " + newSize + " tasks in the list.");
        showLine();
    }

    /**
     * Only displays the message if a task is marked as done
     *
     * @param task task that was marked as done
     */
    public void showMarked(Task task) {
        showLine();
        System.out.println(" Nice! I've marked this task as done:");
        System.out.println("   " + task);
        showLine();
    }

    /**
     * Only displays the message if a task is marked as undone
     *
     * @param task task that was marked as undone
     */
    public void showUnmarked(Task task) {
        showLine();
        System.out.println(" OK, I've marked this task as not done yet:");
        System.out.println("   " + task);
        showLine();
    }

    /**
     * Displays the current list of tasks, formatted with all details
     *
     * @param tasks current list of tasks
     */
    public void showList(TaskList tasks) {
        showLine();
        System.out.println(" Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            int displayNumber = i + 1; // Convert 0-based index to 1-based display
            Task currentTask = tasks.get(i);
            System.out.println(" " + displayNumber + ". " + currentTask);
        }
        showLine();
    }

    /**
     * Displays tasks that match a find query.
     *
     * @param matches tasks matching the query
     */
    public void showFindResults(List<Task> matches) {
        showLine();

        if (matches.isEmpty()) {
            System.out.println(" No matching tasks found.");
            showLine();
            return;
        }

        System.out.println(" Here are the matching tasks in your list:");
        for (int i = 0; i < matches.size(); i++) {
            int displayNumber = i + 1; // Convert 0-based index to 1-based display
            Task matchingTask = matches.get(i);
            System.out.println(" " + displayNumber + ". " + matchingTask);
        }
        showLine();
    }

    public void showHelp() {
        showLine();
        System.out.println(" Available Commands\n");

        printSection("TASK MANAGEMENT", new String[][]{
            {"todo <desc>", "Add a new todo task"},
            {"deadline <desc> /by yyyy-MM-dd", "Add a task with a deadline"},
            {"event <desc> /from <start> /to <end>", "Add an event with a time range"}
        });

        printSection("TASK OPERATIONS", new String[][]{
            {"list", "Show all tasks"},
            {"find <keyword>", "Search tasks by keyword"},
            {"mark <index>", "Mark task as done"},
            {"unmark <index>", "Mark task as not done"},
            {"delete <index>", "Delete task at index"}
        });

        printSection("MISC", new String[][]{
            {"help (or h)", "Show this help message"},
            {"bye", "Exit the program"}
        });

        showLine();
    }

    private void printSection(String title, String[][] entries) {
        System.out.println(" " + title);
        for (String[] entry : entries) {
            System.out.printf(HELP_LINE_FORMAT, entry[0], entry[1]);
        }
        System.out.println();
    }
}
