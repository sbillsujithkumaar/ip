import java.util.Scanner;

public class Leo {
    private static final Task[] tasksList = new Task[100];
    private static int numOfTask = 0;
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
            } else if (input.startsWith("todo ")) {
                // ToDo
                String desc = input.substring(5);
                if (desc.isEmpty()) {
                    error("Input in this format: todo <description>");
                } else {
                    addTask(new ToDo(desc));
                }

            } else if (input.startsWith("deadline ")) {
                // 'deadline <desc> /by <by>'
                String body = input.substring(9);
                int byIdx = body.indexOf(" /by ");
                if (byIdx == -1) {
                    error("Input in this format: deadline <description> /by <deadline>");
                } else {
                    String desc = body.substring(0, byIdx);
                    String by = body.substring(byIdx + 5);
                    if (desc.isEmpty() || by.isEmpty()) {
                        error("Input in this format: deadline <description> /by <deadline>");
                    } else {
                        addTask(new Deadline(desc, by));
                    }
                }

            } else if (input.startsWith("event ")) {
                // 'event <desc> /from <from> /to <to>'
                String body = input.substring(6);
                int fromIdx = body.indexOf(" /from ");
                int toIdx   = body.indexOf(" /to ");

                if (fromIdx == -1 || toIdx == -1 || toIdx <= fromIdx) {
                    error("Input in this format: event <description> /from <start> /to <end>");
                } else {
                    String desc = body.substring(0, fromIdx);
                    String from = body.substring(fromIdx + 7, toIdx);
                    String to   = body.substring(toIdx + 5);
                    if (desc.isEmpty() || from.isEmpty() || to.isEmpty()) {
                        error("Input in this format: event <description> /from <start> /to <end>");
                    } else {
                        addTask(new Event(desc, from, to));
                    }
                }
            } else {
                // Fail
                System.out.println("__________________________________________________________________");
                System.out.println(" Invalid input! Try again");
                System.out.println("__________________________________________________________________");
            }
        }
        scanner.close();
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
