import java.util.Scanner;

public class Leo {
    private static final Task[] tasksList = new Task[100];
    private static int numOfTask = 0;

    // To handle printing out error messages
    private static void error(String msg) {
        System.out.println("__________________________________________________________________");
        System.out.println(" " + msg);
        System.out.println("__________________________________________________________________");
    }

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
        System.out.println(logo);

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
            try {
                if (input.equals("bye")) {
                    exit();
                    break;
                } else if (input.equals("list")) {
                    printTasksList();
                } else if (input.startsWith("mark ")) {
                    mark(input);
                } else if (input.startsWith("unmark ")) {
                    unmark(input);
                } else if (input.startsWith("todo ")) {
                    handleToDo(input);
                } else if (input.startsWith("deadline ")) {
                    handleDeadline(input);
                } else if (input.startsWith("event ")) {
                    handleEvent(input);
                } else {
                    throw new LeoException("Unknown command: " + input);
                }
            } catch (LeoException e) {
                error(e.getMessage());
            }
        }
        scanner.close();
    }

    private static void handleToDo(String input) throws LeoException {
        String desc = input.substring(5).trim();
        if (desc.isEmpty()) {
            throw new LeoException("Todo what? It's an empty todo");
        }
        addTask(new ToDo(desc));
    }

    private static void handleDeadline(String input) throws LeoException {
        String body = input.substring(9).trim();
        int byIdx = body.indexOf(" /by ");

        if (byIdx == -1) {
            throw new LeoException("Wrong format for deadline. Input in this format: deadline <description> /by <deadline>");
        }

        String desc = body.substring(0, byIdx);
        String by = body.substring(byIdx + 5);

        if (desc.isEmpty() || by.isEmpty()) {
            throw new LeoException("Description/Date is empty. Input in this format: deadline <description> /by <deadline>");
        }

        addTask(new Deadline(desc, by));
    }

    private static void handleEvent(String input) throws LeoException {
        String body = input.substring(6).trim();
        int fromIdx = body.indexOf(" /from ");
        int toIdx = body.indexOf(" /to ");

        if (fromIdx == -1 || toIdx == -1 || toIdx <= fromIdx) {
            throw new LeoException("Wrong format for event. Input in this format: event <description> /from <start> /to <end>");
        }

        String desc = body.substring(0, fromIdx);
        String from = body.substring(fromIdx + 7, toIdx);
        String to = body.substring(toIdx + 5);

        if (desc.isEmpty() || from.isEmpty() || to.isEmpty()) {
            throw new LeoException("Description/Dates are empty. Input in this format: event <description> /from <start> /to <end>");
        }

        addTask(new Event(desc, from, to));
    }

    private static void mark(String input) throws LeoException {
        // Get the string for number
        String index = input.substring(5).trim(); // .trim() to be safer
        if (index.isEmpty()) {
            throw new LeoException("No number was inputted");
        }

        // Convert to number
        int taskNum = Integer.parseInt(index) - 1;
        Task currTask = tasksList[taskNum];
        currTask.markAsDone();

        System.out.println("__________________________________________________________________");
        System.out.println(" Nice! I've marked this task as done:");
        System.out.println("   " + currTask);
        System.out.println("__________________________________________________________________");
    }

    private static void unmark(String input) throws LeoException {
        // Get the string for number
        String index = input.substring(7).trim(); // .trim() to be safer
        if (index.isEmpty()) {
            throw new LeoException("No number was inputted");
        }

        // Convert to number
        int taskNum = Integer.parseInt(index) - 1;
        Task currTask = tasksList[taskNum];
        currTask.markAsNotDone();

        System.out.println("__________________________________________________________________");
        System.out.println(" OK, I've marked this task as not done yet:");
        System.out.println("   " + currTask);
        System.out.println("__________________________________________________________________");
    }

    public static void addTask(Task task) {
        tasksList[numOfTask] = task;
        numOfTask++;

        System.out.println("__________________________________________________________________");
        System.out.println(" Got it. I've added this task:");
        System.out.println("   " + task);
        System.out.println(" Now you have " + numOfTask + " tasks in the list.");
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

    public static void exit() {
        System.out.println("__________________________________________________________________");
        System.out.println(" Bye. Hope to see you again soon!");
        System.out.println("__________________________________________________________________");
    }
}
