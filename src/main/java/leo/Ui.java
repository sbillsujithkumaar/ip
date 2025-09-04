package leo;

import leo.tasks.Task;
import leo.tasks.TaskList;

import java.util.Scanner;

public class Ui {
    // To display errors
    public void showError(String msg) {
        System.out.println("__________________________________________________________________");
        System.out.println(" " + msg);
        System.out.println("__________________________________________________________________");
    }

    // Create a scanner object
    private final Scanner scanner = new Scanner(System.in);
    // Uses the same scanner to read
    public String readCommand() {
        return scanner.nextLine();
    }

    public void showLine() {
        System.out.println("__________________________________________________________________");
    }

    public void showLoadingError() {
       showError("I/O Error occured when loading from memory");
    }

    public void showWelcome() {
        String logo = """
         ▄█          ▄████████  ▄██████▄ 
        ███         ███    ███ ███    ███
        ███         ███    █▀  ███    ███
        ███        ▄███▄▄▄     ███    ███
        ███       ▀▀███▀▀▀     ███    ███
        ███         ███    █▄  ███    ███
        ███▌    ▄   ███    ███ ███    ███
        █████▄▄██   ██████████  ▀██████▀ 
        ▀                                
        """;
        System.out.println(logo);

        showLine();
        System.out.println(" Hello! I'm Leo");
        System.out.println(" What can I do for you?");
        showLine();
    }

    public void showBye() {
        showLine();
        System.out.println(" Bye. Hope to see you again soon!");
        showLine();
    }

    public void showAdded(Task task, int newSize) {
        showLine();
        System.out.println(" Got it. I've added this task:");
        System.out.println("   " + task);
        System.out.println(" Now you have " + newSize + " tasks in the list.");
        showLine();
    }

    public void showRemoved(Task task, int newSize) {
        showLine();
        System.out.println(" Noted. I've removed this task:");
        System.out.println("   " + task);
        System.out.println(" Now you have " + newSize + " tasks in the list.");
        showLine();
    }

    public void showMarked(Task task) {
        showLine();
        System.out.println(" Nice! I've marked this task as done:");
        System.out.println("   " + task);
        showLine();
    }

    public void showUnmarked(Task task) {
        showLine();
        System.out.println(" OK, I've marked this task as not done yet:");
        System.out.println("   " + task);
        showLine();
    }

    public void showList(TaskList tasks) {
        showLine();
        System.out.println(" Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println(" " + (i + 1) + ". " + tasks.get(i));
        }
        showLine();
    }
}
