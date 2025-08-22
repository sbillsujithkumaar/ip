import java.util.Scanner;

public class Leo {
    private static final Task[] tasksList = new Task[100];
    private static int numOfTask = 0;

    public static void main(String[] args) {
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
        System.out.println("Hello from\n" + logo);

        greet();
        run();
    }
    public static void greet() {
        System.out.println("__________________________________________________________________");
        System.out.println(" Hello! I'm Leo");
        System.out.println(" What can I do for you?");
        System.out.println("__________________________________________________________________");
    }

    public static void run() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String input = scanner.nextLine();
            if (input.equals("bye")) {
                exit();
                break;
            } else if (input.equals("list")) {
                printTasksList();
            } else if (input.startsWith("mark ")) {
                int taskNum = Integer.parseInt(input.split(" ")[1]) - 1;
                mark(taskNum);
            } else if (input.startsWith("unmark ")) {
                int taskNum = Integer.parseInt(input.split(" ")[1]) - 1;
                unmark(taskNum);
            } else {
                addTask(input);
            }
        }
        scanner.close();
    }

    public static void addTask(String description) {
        Task task = new Task(description);
        tasksList[numOfTask] = task;
        numOfTask++;

        System.out.println("__________________________________________________________________");
        System.out.println(" added: " + description);
        System.out.println("__________________________________________________________________");
    }

    public static void printTasksList() {
        System.out.println("__________________________________________________________________");
        System.out.println(" Here are the tasks in your list:");
        for (int i = 0; i < numOfTask; i++) {
            System.out.println(" " + (i + 1) + ". " + tasksList[i]);
        }
        System.out.println("__________________________________________________________________");
    }

    public static void mark(int taskNum) {
        Task currTask = tasksList[taskNum];
        currTask.markAsDone();


        System.out.println("__________________________________________________________________");
        System.out.println(" Nice! I've marked this task as done:");
        System.out.println("   " + currTask);
        System.out.println("__________________________________________________________________");
    }

    public static void unmark(int taskNum) {
        Task currTask = tasksList[taskNum];
        currTask.markAsNotDone();

        System.out.println("__________________________________________________________________");
        System.out.println(" OK, I've marked this task as not done yet:");
        System.out.println("   " + currTask);
        System.out.println("__________________________________________________________________");
    }

    public static void exit() {
        System.out.println("__________________________________________________________________");
        System.out.println(" Bye. Hope to see you again soon!");
        System.out.println("__________________________________________________________________");
    }
}
