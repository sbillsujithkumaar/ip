package leo;

import leo.commands.AddCommand;
import leo.commands.Command;
import leo.commands.DeleteCommand;
import leo.commands.ExitCommand;
import leo.commands.FindCommand;
import leo.commands.ListCommand;
import leo.commands.MarkCommand;
import leo.commands.UnmarkCommand;
import leo.tasks.Deadline;
import leo.tasks.Event;
import leo.tasks.ToDo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Parser {
    private static final DateTimeFormatter DEADLINE_IN = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    /**
     * Parses a full user input line into a concrete {@link leo.command.Command}.
     *
     * @param fullCommand string input line from the user (e.g., "todo read book").
     *
     * @return a {@code Command}, which will eventually run execute method.
     * @throws LeoException if the command is empty or unknown (invalid).
     */
    public static Command parse(String fullCommand) throws LeoException {

        String input = fullCommand.trim();

        if (input.isEmpty()) {
            throw new LeoException("Nothing was commanded");
        }

        if (input.equals("bye")) {
            return new ExitCommand();

        } else if (input.equals("list")) {
            return new ListCommand();

        } else if (input.startsWith("mark ")) {
            int idx = parseIndex(input, "mark"); // 0-based
            return new MarkCommand(idx);

        } else if (input.startsWith("unmark ")) {
            int idx = parseIndex(input, "unmark"); // 0-based
            return new UnmarkCommand(idx);

        } else if (input.startsWith("todo ")) {
            String desc = parseTodo(input);
            return new AddCommand(new ToDo(desc));

        } else if (input.startsWith("deadline ")) {
            Deadline dl = parseDeadline(input);
            return new AddCommand(dl);

        } else if (input.startsWith("event ")) {
            Event ev = parseEvent(input);
            return new AddCommand(ev);

        } else if (input.startsWith("delete ")) {
            int idx = parseIndex(input, "delete"); // 0-based
            return new DeleteCommand(idx);

        } else if (input.startsWith("find ")) {
            String keyword = input.substring(5);
            return new FindCommand(keyword);

        } else {
            throw new LeoException("Unknown command: " + input);
        }
    }

    private static int parseIndex(String input, String commandWord) throws LeoException {
        String rest = input.substring(commandWord.length()).trim();
        if (rest.isEmpty()) {
            throw new LeoException("No number was inputted");
        }

        try {
            // convert 1 based to 0 based
            return Integer.parseInt(rest) - 1;
        } catch (NumberFormatException e) {
            throw new LeoException("Not a valid number");
        }
    }

    private static String parseTodo(String input) throws LeoException {
        // "todo "
        String desc = input.substring(5).trim();
        if (desc.isEmpty()) {
            throw new LeoException("Todo what? It's an empty todo");
        }

        return desc;
    }

    private static Deadline parseDeadline(String input) throws LeoException {
        // "deadline "
        String body = input.substring(9).trim();
        int byIdx = body.indexOf(" /by ");

        if (byIdx == -1) {
            throw new LeoException("Wrong format. Input: deadline <description> /by <deadline>");
        }

        String desc = body.substring(0, byIdx).trim();
        String by = body.substring(byIdx + 5).trim();

        if (desc.isEmpty() || by.isEmpty()) {
            throw new LeoException("Description/Date is empty. Input: deadline <description> /by <deadline>");
        }

        try {
            LocalDate parsedBy = LocalDate.parse(by, DEADLINE_IN);
            return new Deadline(desc, parsedBy);
        } catch (DateTimeParseException e) {
            throw new LeoException("Invalid date format. Enter like yyyy-MM-dd");
        }
    }

    private static Event parseEvent(String input) throws LeoException {
        // "event "
        String body = input.substring(6).trim();
        int fromIdx = body.indexOf(" /from ");
        int toIdx = body.indexOf(" /to ");

        if (fromIdx == -1 || toIdx == -1 || toIdx <= fromIdx) {
            throw new LeoException("Wrong format. Input:  event <description> /from <start> /to <end>");
        }

        String desc = body.substring(0, fromIdx).trim();
        String from = body.substring(fromIdx + 7, toIdx).trim();
        String to = body.substring(toIdx + 5).trim();

        if (desc.isEmpty() || from.isEmpty() || to.isEmpty()) {
            throw new LeoException("Description/Dates are empty. Input:  event <description> /from <start> /to <end>");
        }

        return new Event(desc, from, to);
    }
}
