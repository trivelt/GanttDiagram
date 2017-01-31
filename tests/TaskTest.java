import org.junit.*;
import java.awt.*;
import static org.junit.Assert.*;

public class TaskTest {

    @Test
    public void testAttributes(){
        Task task = new Task("Foo", 55, Color.RED, 7, 99);
        assertEquals("Foo", task.getName());
        assertEquals(55, task.getLength());
        assertEquals(Color.RED, task.getColor());
        assertEquals(7, task.getX());
        assertEquals(99, task.getY());

        task.setPos(39, 44);
        assertEquals(39, task.getX());
        assertEquals(44, task.getY());

        task.setLength(8);
        assertEquals(8, task.getLength());

        task.setColor(Color.GREEN);
        assertEquals(Color.GREEN, task.getColor());

        task.setName("Bar");
        assertEquals("Bar", task.getName());
    }
}
