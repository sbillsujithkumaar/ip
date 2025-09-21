package leo;

import leo.commands.Command;
import leo.commands.ListCommand;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ParserTest {

    @Test
    void list_returnsListCommand() throws LeoException {
        Command c = Parser.parse("list");
        assertNotNull(c);
        assertTrue(c instanceof ListCommand);
        assertFalse(c.isExit());
    }

    @Test
    void deadlineWrongDateFormat_throws() {
        // wrong format: uses slashes instead of yyyy-MM-dd
        LeoException ex = assertThrows(LeoException.class, () -> Parser.parse("deadline return /by 2025/12/31"));
        assertTrue(ex.getMessage().toLowerCase().contains("invalid"));
    }

}
