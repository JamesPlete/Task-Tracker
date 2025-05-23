package controller;

import model.Status;
import model.Task;

import java.io.File;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import com.fasterxml.jackson.databind.ObjectMapper;

import static controller.TaskControllerUtility.getAllTasks;

public class TaskController {

    private List<Task> taskList;
    private int idCounter;
    private final ObjectMapper mapper;
    private final File jsonFile;

    public TaskController() {
        taskList = new LinkedList<>();
        mapper = new ObjectMapper();
        jsonFile = new File("database.json");
        TaskControllerUtility.verifyFile(jsonFile);
        taskList = getAllTasks(mapper, jsonFile);
        idCounter = taskList.isEmpty() ? 0 : taskList.getLast().getId();
    }

    public void addTask(Task task) {
        task.setId(++idCounter);
        taskList.add(task);
        TaskControllerUtility.saveToFile(taskList, mapper, jsonFile);
    }

    public void updateTask(int id, String description) throws TaskControllerUtility.TaskDuplicateException {
        Task task = TaskControllerUtility.taskFinderHelper(id, taskList);
        task.updateDescription(description);
        TaskControllerUtility.saveToFile(taskList, mapper, jsonFile);
    }

    public void updateProgress(int id, String status) throws TaskControllerUtility.TaskNotFoundException {
        try {
            Task task = TaskControllerUtility.taskFinderHelper(id, taskList);
            Status progress = Arrays.stream(Status.values())
                    .filter(s -> s.getStatus().equals(status))
                    .findFirst()
                    .orElseThrow(TaskControllerUtility.TaskNotFoundException::new);
            task.updateStatus(progress);
            TaskControllerUtility.saveToFile(taskList, mapper, jsonFile);
        } catch (TaskControllerUtility.TaskNotFoundException e) {
            throw new TaskControllerUtility.TaskNotFoundException();
        }
    }

    public void deleteTask(int id) throws TaskControllerUtility.TaskNotFoundException {
        try {
            taskList.removeIf(t -> t.getId() == id);

            TaskControllerUtility.adjustList(id, taskList);
            TaskControllerUtility.saveToFile(taskList, mapper, jsonFile);
            idCounter = taskList.getLast().getId();
        } catch (NullPointerException e) {
            throw new TaskControllerUtility.TaskNotFoundException();
        }
    }

    public void listTasks() {
        taskList.forEach(System.out::println);
    }

    public void listBasedOnProgress(String status) {
        taskList.stream()
                .filter(t -> t.getStatus().equals(status))
                .forEach(System.out::println);
    }
}
