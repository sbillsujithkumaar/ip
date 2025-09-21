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

/**
 * Parses user input strings into concrete {@code Command} instances.
 */
public class Parser {
    // Command prefixes
    private static final String BYE_COMMAND = "bye";
    private static final String LIST_COMMAND = "list";
    private static final String MARK_COMMAND = "mark ";
    private static final String UNMARK_COMMAND = "unmark ";
    private static final String DELETE_COMMAND = "delete ";
    
    // Command parameter separators
    private static final String DEADLINE_SEPARATOR = " /by ";
    private static final String EVENT_FROM_SEPARATOR = " /from ";
    private static final String EVENT_TO_SEPARATOR = " /to ";
    
    // Substring indices for command parsing
    private static final int FIND_KEYWORD_START = 5;
    private static final int TODO_DESC_START = 5;
    private static final int DEADLINE_BODY_START = 9;
    private static final int EVENT_BODY_START = 6;
    private static final int DEADLINE_SEPARATOR_LENGTH = 5;
    private static final int EVENT_FROM_SEPARATOR_LENGTH = 7;
    private static final int EVENT_TO_SEPARATOR_LENGTH = 5;
    
    
    // Date format
    private static final DateTimeFormatter DEADLINE_IN = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    
    // Index conversion
    private static final int USER_INDEX_TO_ZERO_BASED_OFFSET = 1;

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
        
        // Handle empty input early
        if (input.isEmpty()) {
            throw new LeoException("Nothing was commanded");
        }
        
        // Try to recognize simple commands first
        Command simpleCommand = parseSimpleCommand(input);
        if (simpleCommand != null) {
            return simpleCommand;
        }
        
        // Try to parse commands with parameters
        Command parameterCommand = parseParameterCommand(input);
        if (parameterCommand != null) {
            return parameterCommand;
        }
        
        // Unknown command
        throw new LeoException("Unknown command: " + input);
    }
    
    /**
     * Parses simple commands that don't require parameters.
     */
    private static Command parseSimpleCommand(String input) {
        if (input.equals(BYE_COMMAND)) {
            return new ExitCommand();
        } else if (input.equals(LIST_COMMAND)) {
            return new ListCommand();
        } else {
            // No simple command matched - this is expected behavior
            return null;
        }
    }
    
    /**
     * Parses commands that require parameters.
     */
    private static Command parseParameterCommand(String input) throws LeoException {
        String firstToken = getFirstToken(input);
        
        switch (firstToken) {
            case "mark":
                return parseIndexCommand(input, MARK_COMMAND, MarkCommand::new);
            case "unmark":
                return parseIndexCommand(input, UNMARK_COMMAND, UnmarkCommand::new);
            case "delete":
                return parseIndexCommand(input, DELETE_COMMAND, DeleteCommand::new);
            case "find":
                return parseFindCommand(input);
            case "todo":
                return parseTodoCommand(input);
            case "deadline":
                return parseDeadlineCommand(input);
            case "event":
                return parseEventCommand(input);
            default:
                // No parameter command matched - this is expected behavior
                return null;
        }
    }

    /**
     * Extracts the first token (command word) from the input string.
     */
    private static String getFirstToken(String input) {
        int spaceIndex = input.indexOf(' ');
        if (spaceIndex == -1) {
            return input.trim();
        }
        return input.substring(0, spaceIndex).trim();
    }
    
    /**
     * Parses commands that require an index parameter.
     */
    private static Command parseIndexCommand(String input, String commandPrefix, 
                                           IndexCommandFactory factory) throws LeoException {
        String trimmedCommandPrefix = commandPrefix.trim();
        int taskIndex = parseIndex(input, trimmedCommandPrefix);
        return factory.create(taskIndex);
    }
    
    /**
     * Functional interface for creating index-based commands.
     */
    @FunctionalInterface
    private interface IndexCommandFactory {
        Command create(int index) throws LeoException;
    }
    
    /**
     * Parses the find command.
     */
    private static Command parseFindCommand(String input) {
        String searchKeyword = input.substring(FIND_KEYWORD_START);
        return new FindCommand(searchKeyword);
    }
    
    /**
     * Parses the todo command.
     */
    private static Command parseTodoCommand(String input) throws LeoException {
        String taskDescription = parseTodo(input);
        ToDo newTodo = new ToDo(taskDescription);
        return new AddCommand(newTodo);
    }
    
    /**
     * Parses the deadline command.
     */
    private static Command parseDeadlineCommand(String input) throws LeoException {
        Deadline newDeadline = parseDeadline(input);
        return new AddCommand(newDeadline);
    }
    
    /**
     * Parses the event command.
     */
    private static Command parseEventCommand(String input) throws LeoException {
        Event newEvent = parseEvent(input);
        return new AddCommand(newEvent);
    }

    private static int parseIndex(String input, String commandWord) throws LeoException {
        // Extract the index part after the command word
        int commandWordLength = commandWord.length();
        String indexPart = input.substring(commandWordLength).trim();
        
        if (indexPart.isEmpty()) {
            throw new LeoException("No number was inputted");
        }

        try {
            // Parse the user-provided index (1-based)
            int userProvidedIndex = Integer.parseInt(indexPart);
            
            // Convert from 1-based user input to 0-based array index
            int arrayIndex = userProvidedIndex - USER_INDEX_TO_ZERO_BASED_OFFSET;
            return arrayIndex;
        } catch (NumberFormatException e) {
            throw new LeoException("Not a valid number");
        }
    }

    private static String parseTodo(String input) throws LeoException {
        String desc = input.substring(TODO_DESC_START).trim();
        if (desc.isEmpty()) {
            throw new LeoException("Todo what? It's an empty todo");
        }

        return desc;
    }

    private static Deadline parseDeadline(String input) throws LeoException {
        String body = input.substring(DEADLINE_BODY_START).trim();
        int byIdx = body.indexOf(DEADLINE_SEPARATOR);

        if (byIdx == -1) {
            throw new LeoException("Wrong format. Input: deadline <description> /by <deadline>");
        }

        String desc = body.substring(0, byIdx).trim();
        String by = body.substring(byIdx + DEADLINE_SEPARATOR_LENGTH).trim();

        // Check if description and date are both non-empty
        boolean isDescriptionEmpty = desc.isEmpty();
        boolean isDateEmpty = by.isEmpty();
        if (isDescriptionEmpty || isDateEmpty) {
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
        String body = input.substring(EVENT_BODY_START).trim();
        int fromIdx = body.indexOf(EVENT_FROM_SEPARATOR);
        int toIdx = body.indexOf(EVENT_TO_SEPARATOR);

        // Check if separators exist and are in correct order
        boolean isFromSeparatorMissing = fromIdx == -1;
        boolean isToSeparatorMissing = toIdx == -1;
        boolean isToBeforeFrom = toIdx <= fromIdx;
        
        if (isFromSeparatorMissing || isToSeparatorMissing || isToBeforeFrom) {
            throw new LeoException("Wrong format. Input:  event <description> /from <start> /to <end>");
        }

        String desc = body.substring(0, fromIdx).trim();
        String from = body.substring(fromIdx + EVENT_FROM_SEPARATOR_LENGTH, toIdx).trim();
        String to = body.substring(toIdx + EVENT_TO_SEPARATOR_LENGTH).trim();

        // Check if all required fields are non-empty
        boolean isDescriptionEmpty = desc.isEmpty();
        boolean isFromEmpty = from.isEmpty();
        boolean isToEmpty = to.isEmpty();
        
        if (isDescriptionEmpty || isFromEmpty || isToEmpty) {
            throw new LeoException("Description/Dates are empty. Input:  event <description> /from <start> /to <end>");
        }

        return new Event(desc, from, to);
    }
}
