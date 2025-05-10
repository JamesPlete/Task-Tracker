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

    public void updateTask(int ID, String description) {
        try {
            Task task = taskFinderHelper(ID);
            isListEmpty();
            if (!taskList.contains(task)) throw new TaskNotFoundException();
            task.updateDescription(description);
        } catch (TaskNotFoundException e){
            System.out.println("Task can not found. Please try again.");
        }
    }

    public List<Task> getTaskList() {
        return taskList;
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

    public void markProgress(Status status, int ID) {
        try {
            Task task = taskFinderHelper(ID);
            if (taskList.contains(task)) {
                task.updateStatus(status);
            }
        } catch (TaskNotFoundException e) {
            System.out.println("Task can not be found on the task list. Please try again.");

        }

    }

    private Task taskFinderHelper(int id) throws TaskNotFoundException{
        return taskList
                .stream()
                .filter(t -> t.getId() == id)
                .findAny()
                .orElseThrow(NoSuchFieldError::new);
    }

    private void isListEmpty(){
        if (taskList.isEmpty()){
            System.out.println("List is empty. Please try again.");
            return;
        }
    }

    public static class TaskNotFoundException extends Exception{}
}
