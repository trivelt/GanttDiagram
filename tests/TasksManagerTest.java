import org.junit.*;
import java.awt.*;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class TasksManagerTest {

    @Test
    public void testCreateTasks(){
        Parameters parameters = Parameters.getInstance();
        parameters.numberOfLines = 2;
        parameters.numberOfTasks = 15;

        TasksManager tasksManager = new TasksManager();
        assertTrue(tasksManager.getTasks().isEmpty());

        tasksManager.createTasks();
        ArrayList<Task> tasks = tasksManager.getTasks();
        assertEquals(15, tasks.size());
    }

    @Test
    public void testTaskCoordinates(){
        Parameters parameters = Parameters.getInstance();
        parameters.numberOfLines = 1;
        parameters.numberOfTasks = 3;

        TasksManager tasksManager = new TasksManager();
        tasksManager.createTasks();
        ArrayList<Task> tasks = tasksManager.getTasks();
        assertEquals(3, tasks.size());

        Task firstTask = tasks.get(0);
        Task secondTask = tasks.get(1);
        Task thirdTask = tasks.get(2);

        assertTrue(firstTask.getX() < secondTask.getX());
        assertTrue(firstTask.getX() + firstTask.getLength() < secondTask.getX());
        assertTrue(thirdTask.getX() > secondTask.getX());
        assertEquals(firstTask.getY(), secondTask.getY());
        assertEquals(firstTask.getY(), thirdTask.getY());

        Task retrievedTask = tasksManager.getTask(firstTask.getX() + firstTask.getLength()/2, firstTask.getY());
        assertEquals(firstTask, retrievedTask);

        retrievedTask = tasksManager.getTask(0, 9999);
        assertEquals(null, retrievedTask);

        assertFalse(tasksManager.isCollisionAfterResize(firstTask, firstTask.getLength()-5));
        assertTrue(tasksManager.isCollisionAfterResize(secondTask, secondTask.getLength()*2));
    }

    @Test
    public void testTasksCollision(){
        Parameters parameters = Parameters.getInstance();
        parameters.numberOfLines = 3;
        parameters.numberOfTasks = 2;

        TasksManager tasksManager = new TasksManager();
        tasksManager.createTasks();
        ArrayList<Task> tasks = tasksManager.getTasks();
        assertEquals(2, tasks.size());

        Task firstTask = tasks.get(0);
        Task secondTask = tasks.get(1);

        int newY = tasksManager.getBestYPosAfterMove(firstTask.getY()-UI.lineHeight);
        assertFalse(tasksManager.isCollisionAfterMove(firstTask, firstTask.getX(), newY));
        assertTrue(tasksManager.isCollisionAfterMove(firstTask, secondTask.getX(), firstTask.getY()));
    }

    @Test
    public void testRemoveTasks(){
        Parameters parameters = Parameters.getInstance();
        parameters.numberOfLines = 3;
        parameters.numberOfTasks = 4;

        TasksManager tasksManager = new TasksManager();
        tasksManager.createTasks();
        ArrayList<Task> tasks = tasksManager.getTasks();
        assertEquals(4, tasks.size());

        Task task1 = tasks.get(0);
        Task task2 = tasks.get(1);
        Task task3 = tasks.get(2);
        Task task4 = tasks.get(3);

        tasksManager.removeTask(task2);
        assertEquals(3, tasks.size());

        tasksManager.removeTask(task1);
        tasksManager.removeTask(task4);
        assertEquals(1, tasks.size());

        tasksManager.removeTask(task3);
        assertTrue(tasks.isEmpty());
    }
}
