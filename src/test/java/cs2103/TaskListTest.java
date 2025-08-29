package cs2103;

import cs2103.exception.TommyException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TaskListTest {

    @Test //test the adding of new tasks and the bumping of sixe
    void addTask() throws TommyException {

        Task[] backing = new Task[100];
        backing[0] = new Todo("A");
        backing[2] = new Todo("C");

        TaskList list = new TaskList(backing, 2);


        Task b = new Todo("B");
        list.addTask(b);


        assertEquals(3, list.getSize(), "Size should increment after add");
        assertSame(backing[1], b, "New task should be placed in the first null slot");
        assertEquals("A", ((Todo) backing[0]).getName());
        assertEquals("B", ((Todo) backing[1]).getName());
        assertEquals("C", ((Todo) backing[2]).getName());
    }

    @Test //correct exception and message
    void addTask_whenFull_throwsTommyException() {
        Task[] full = new Task[100];
        for (int i = 0; i < full.length; i++) full[i] = new Todo("T" + i);
        TaskList list = new TaskList(full, 100);

        TommyException ex = assertThrows(TommyException.class,
                () -> list.addTask(new Todo("overflow")));
        assertEquals("Task list is full", ex.getMessage());
        assertEquals(100, list.getSize(), "Size should remain at capacity");
    }

    @Test //check bounds
    void getTask_returnsTaskForValidIndex_elseNull() {
        Task[] arr = new Task[100];
        arr[0] = new Todo("only");
        TaskList list = new TaskList(arr, 1);

        assertNotNull(list.getTask(0), "Valid index should return a task");
        assertNull(list.getTask(-1), "Negative index should return null");
        assertNull(list.getTask(1), "Index == size should return null");
        assertNull(list.getTask(50), "Far out-of-bounds should return null");
    }
}
