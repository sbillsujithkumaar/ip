package leo.tasks;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TaskListTest {

    @Test
    void add_increases_size_and_preserves_item() {
        TaskList list = new TaskList();
        ToDo t = new ToDo("read book");
        list.add(t);

        assertEquals(1, list.size());
        assertSame(t, list.get(0)); // same object back
    }

    @Test
    void remove_returns_item_and_decreases_size() {
        TaskList list = new TaskList();
        ToDo t1 = new ToDo("a");
        ToDo t2 = new ToDo("b");
        list.add(t1);
        list.add(t2);

        Task removed = list.remove(0);

        assertSame(t1, removed);
        assertEquals(1, list.size());
        assertSame(t2, list.get(0));
    }
}
