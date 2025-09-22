package leo;

import leo.commands.AddCommand;
import leo.commands.Command;
import leo.commands.ExitCommand;
import leo.commands.FindCommand;
import leo.commands.HelpCommand;
import leo.commands.ListCommand;
import leo.commands.MarkCommand;
import leo.tasks.Deadline;
import leo.tasks.Event;
import leo.tasks.ToDo;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Essential test suite for the Parser class.
 * Tests the parse method with the most important scenarios.
 */
public class ParserTest {

    // Test basic commands
    @Test
    public void parse_byeCommand_returnsExitCommand() throws LeoException {
        Command result = Parser.parse("bye");
        assertInstanceOf(ExitCommand.class, result);
        assertTrue(result.isExit());
    }

    @Test
    public void parse_listCommand_returnsListCommand() throws LeoException {
        Command result = Parser.parse("list");
        assertInstanceOf(ListCommand.class, result);
    }

    @Test
    public void parse_helpCommand_returnsHelpCommand() throws LeoException {
        Command result = Parser.parse("help");
        assertInstanceOf(HelpCommand.class, result);
    }

    // Test error handling
    @Test
    public void parse_emptyInput_throwsException() {
        LeoException exception = assertThrows(LeoException.class, () -> Parser.parse(""));
        assertEquals("Nothing was commanded", exception.getMessage());
    }

    @Test
    public void parse_unknownCommand_throwsException() {
        LeoException exception = assertThrows(LeoException.class, () -> Parser.parse("unknown"));
        assertEquals("Unknown command: unknown", exception.getMessage());
    }

    // Test todo command
    @Test
    public void parse_todoCommand_returnsAddCommandWithToDo() throws LeoException {
        Command result = Parser.parse("todo read book");
        assertInstanceOf(AddCommand.class, result);
        AddCommand addCommand = (AddCommand) result;
        assertInstanceOf(ToDo.class, addCommand.getTask());
        assertEquals("read book", addCommand.getTask().getDescription());
    }

    @Test
    public void parse_todoCommandEmptyDescription_throwsException() {
        LeoException exception = assertThrows(LeoException.class, () -> Parser.parse("todo"));
        assertEquals("Todo what? It's an empty todo", exception.getMessage());
    }

    // Test deadline command
    @Test
    public void parse_deadlineCommand_returnsAddCommandWithDeadline() throws LeoException {
        Command result = Parser.parse("deadline submit assignment /by 2024-12-31");
        assertInstanceOf(AddCommand.class, result);
        AddCommand addCommand = (AddCommand) result;
        assertInstanceOf(Deadline.class, addCommand.getTask());
        assertEquals("submit assignment", addCommand.getTask().getDescription());
        
        Deadline deadline = (Deadline) addCommand.getTask();
        assertEquals(LocalDate.of(2024, 12, 31), deadline.getBy());
    }

    @Test
    public void parse_deadlineCommandMissingBy_throwsException() {
        LeoException exception = assertThrows(LeoException.class, () -> 
            Parser.parse("deadline submit assignment"));
        assertEquals("Wrong format. Input: deadline <description> /by <deadline>", exception.getMessage());
    }

    @Test
    public void parse_deadlineCommandInvalidDateFormat_throwsException() {
        LeoException exception = assertThrows(LeoException.class, () -> 
            Parser.parse("deadline submit assignment /by 31/12/2024"));
        assertEquals("Invalid date format. Enter like yyyy-MM-dd", exception.getMessage());
    }

    // Test event command
    @Test
    public void parse_eventCommand_returnsAddCommandWithEvent() throws LeoException {
        Command result = Parser.parse("event team meeting /from 2pm /to 3pm");
        assertInstanceOf(AddCommand.class, result);
        AddCommand addCommand = (AddCommand) result;
        assertInstanceOf(Event.class, addCommand.getTask());
        assertEquals("team meeting", addCommand.getTask().getDescription());
        
        Event event = (Event) addCommand.getTask();
        assertEquals("2pm", event.getFrom());
        assertEquals("3pm", event.getTo());
    }

    @Test
    public void parse_eventCommandMissingFrom_throwsException() {
        LeoException exception = assertThrows(LeoException.class, () -> 
            Parser.parse("event team meeting /to 3pm"));
        assertEquals("Wrong format. Input:  event <description> /from <start> /to <end>", exception.getMessage());
    }

    // Test find command
    @Test
    public void parse_findCommand_returnsFindCommand() throws LeoException {
        Command result = Parser.parse("find book");
        assertInstanceOf(FindCommand.class, result);
    }

    @Test
    public void parse_findCommandEmptyKeyword_throwsException() {
        LeoException exception = assertThrows(LeoException.class, () -> Parser.parse("find"));
        assertEquals("Find what? Provide a keyword!", exception.getMessage());
    }

    // Test mark command
    @Test
    public void parse_markCommand_returnsMarkCommand() throws LeoException {
        Command result = Parser.parse("mark 1");
        assertInstanceOf(MarkCommand.class, result);
    }

    @Test
    public void parse_markCommandInvalidIndex_throwsException() {
        LeoException exception = assertThrows(LeoException.class, () -> Parser.parse("mark abc"));
        assertEquals("Not a valid number", exception.getMessage());
    }
}
