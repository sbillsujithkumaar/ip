package leo.tasks;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

public class TaskListTest {

    @Test
    void addIncreasesSize_preservesItem() {
        TaskList list = new TaskList();
        ToDo t = new ToDo("read book");
        list.add(t);

        assertEquals(1, list.size());
        assertSame(t, list.get(0)); // same object back
    }

    @Test
    void removeReturnsItem_decreasesSize() {
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
