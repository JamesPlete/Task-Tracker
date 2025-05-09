import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class TaskManager {
    private final List<Task> taskList;

    public TaskManager(){
        taskList = new ArrayList<>();
    }

    public void addTask(Task task){
        if (taskList.contains(task)){
            System.out.println("Error. Task is already in the list.");
            return;

        }

        taskList.add(task);
        System.out.println("Task added successfully.");
    }

    public void deleteTask(int ID) throws TaskNotFoundException {
        isListEmpty();
        Task toBeRemoved = taskList.stream()
                .filter(task -> task.getId() == ID)
                .findAny()
                .orElseThrow(TaskNotFoundException :: new);

        taskList.remove(toBeRemoved);
    }

    public void listTasks(){
        isListEmpty();
        taskList.forEach(System.out :: println);
    }

    public void listBasedOnProgress(Status status){
        isListEmpty();
        Predicate <Task> checker = task -> task.getStatus() == status;
        taskList.stream()
                .filter(checker)
                .forEach(System.out :: println);
    }

    public void markProgress(Status status, Task task) {
        if (taskList.contains(task)){
            task.updateStatus(status);
            return;
        }

        System.out.println("Task can not be found on the task list. Please try again.");
    }

    private void isListEmpty(){
        if (taskList.isEmpty()){
            System.out.println("List is empty.");
            return;
        }
    }

    public class TaskNotFoundException extends Exception{}
}
