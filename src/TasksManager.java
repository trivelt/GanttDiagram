import java.awt.*;
import java.util.ArrayList;

public class TasksManager {
    private ArrayList<Task> tasks;

    public TasksManager() {
        tasks = new ArrayList<>();
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public void createTasks() {
        Parameters parameters = Parameters.getInstance();

        int tasksPerLine = parameters.numberOfTasks / parameters.numberOfLines;
        int additionalTasks = parameters.numberOfTasks % parameters.numberOfLines;

//        System.out.println("numberOfTasks=" + parameters.numberOfTasks);
//        System.out.println("numberOfLines=" + parameters.numberOfLines);
//        System.out.println("tasksPerLine=" + tasksPerLine);
//        System.out.println("additionalTasks=" + additionalTasks);

        int maxLengthOfTaskInNormalLine = 0;
        if(tasksPerLine != 0) {
            maxLengthOfTaskInNormalLine = UI.lineWidth / tasksPerLine;
        }
        int maxLengthOfTaskInLastLine = UI.lineWidth / (tasksPerLine + additionalTasks);

        int taskCounter = 1;
        for(int i=1; i<=parameters.numberOfLines; i++) {
            int numberOfTasks = tasksPerLine;
            int taskLength = maxLengthOfTaskInNormalLine;

            if(i == parameters.numberOfLines) {
                numberOfTasks += additionalTasks;
                taskLength = maxLengthOfTaskInLastLine;
            }

            for(int k=0;k<numberOfTasks; k++) {
                Task task = new Task("Task " + Integer.toString(taskCounter), taskLength-5, Color.GREEN, 85+taskLength*k, UI.lineHeight*(i-1)+UI.taskVerticalOffset);
                tasks.add(task);
                taskCounter++;
            }
        }
    }

    public Task getTask(int x, int y) {
        for(Task task : tasks) {
            if(x >= task.getX() && x <= task.getX()+task.getLength() && y >= task.getY() && y <= task.getY()+UI.taskHeight) {
                return task;
            }
        }
        return null;
    }

    public boolean isCollisionAfterResize(Task task, int newLength) {
        for(Task otherTask : tasks) {
            if(otherTask == task)
                continue;

            if(otherTask.getX() <= task.getX() + newLength && otherTask.getX() >= task.getX() + task.getLength())
                return true;
        }
        return false;
    }

    public boolean isCollisionAfterMove(Task task, int newX, int newY) {
        return true;
    }


}
