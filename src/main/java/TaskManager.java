import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class TaskManager {
    private final List<Task> taskList;

    public TaskManager(){
        taskList = new ArrayList<>();
    }

    public void addTask(Task task){
        taskList.add(task);
    }

    public void deleteTask(int ID){
        Task toBeRemoved = taskList.stream()
                .filter(task -> task.getId() == ID)
                .findAny()
                .orElseThrow(RuntimeException :: new);

        taskList.remove(toBeRemoved);
    }

    public void listTasks(){
        taskList.forEach(System.out :: println);
    }

    public void listBasedOnProgress(Status status){
        Predicate <Task> checker = task -> task.getStatus() == status;
        taskList.stream()
                .filter(checker)
                .forEach(System.out :: println);
    }

    public void markProgress(Status status, Task task) {
        if (taskList.contains(task)){
            task.updateStatus(status);
        }
    }
}
