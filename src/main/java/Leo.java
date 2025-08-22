import java.util.Scanner;

public class Leo {
    private static final String[] tasksList = new String[100];
    private static int numOfTask = 0;

    public static void main(String[] args) {
        String logo = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";
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
            } else {
                addTask(input);
            }
            System.out.println("__________________________________________________________________");
            System.out.println(" " + input);
            System.out.println("__________________________________________________________________");
        }
        scanner.close();
    }

    public static void addTask(String task) {
        tasksList[numOfTask] = task;
        numOfTask++;
        System.out.println("__________________________________________________________________");
        System.out.println(" added: " + task);
        System.out.println("__________________________________________________________________");
    }

    public static void printTasksList() {
        System.out.println("__________________________________________________________________");
        for (int i = 0; i < numOfTask; i++) {
            System.out.println((i + 1) + ". " + tasksList[i]);
        }
        System.out.println("__________________________________________________________________");
    }

    public static void exit() {
        System.out.println("__________________________________________________________________");
        System.out.println(" Bye. Hope to see you again soon!");
        System.out.println("__________________________________________________________________");
    }
}
