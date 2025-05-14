import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TaskManager {

    private final List<Task> taskList;
    private int idCounter = 0;

    public TaskManager() {
        taskList = new ArrayList<>();
    }

    public void addTask(Task task) {
        taskList.add(task);
        System.out.println("Task added.");
    }


    public void updateTask(int ID, String description) throws TaskDuplicateException {
        if (taskList.isEmpty()) {

            System.out.println("List is empty.");
            return;
        }
        try {
            Task task = taskFinderHelper(ID);
            if (!taskList.contains(task)) {
                throw new TaskNotFoundException();
            }

            if (duplicateChecker(description)) {
                task.updateDescription(description);
                System.out.println("Task description updated.");
            } else {
                throw new TaskDuplicateException();
            }
        } catch (TaskNotFoundException e) {
            System.out.println("Task not found. Please try again.");
        }
    }

    public void deleteTask(int id) throws TaskNotFoundException {
        if (taskList.isEmpty()) {

            System.out.println("List is empty.");
            return;
        }
        Task toBeRemoved = taskFinderHelper(id);
        idCounter--;
        taskList.remove(toBeRemoved);
        System.out.println("Task deleted.");
    }

    public void listTasks() {

        if (taskList.isEmpty()) {
            System.out.println("List is empty.");
            return;
        }
        System.out.println("List of tasks: ");

        taskList.forEach(System.out::println);
    }

    public void listBasedOnProgress(String status) {

        if (taskList.isEmpty()) {
            System.out.println("List is empty.");
        } else {
            Predicate<Task> checker = task -> task.getStatus().equals(status);
            System.out.println(status + " list: ");
            taskList.stream()
                    .filter(checker)
                    .forEach(System.out::println);
        }
    }

    public void updateProgress(int ID, String status) {
        if (taskList.isEmpty()) {

            System.out.println("List is empty.");
            return;
        }
        try {
            Task task = taskFinderHelper(ID);
            if (taskList.contains(task)) {
                task.updateStatus(status);
                System.out.println("Task status updated.");
            }
        } catch (TaskNotFoundException e) {
            System.out.println("Task not found on the task list. Please try again.");
        }
    }

    public List<Task> getTaskList() {
        return taskList;
    }

    private boolean isQueryValid(String query) {
        List<String> structuredQuery = queryOrganizer(query);

        List<String> queryKeys = List.of("add", "delete", "mark", "update", "list");
        List<String> status = List.of("to-do", "in-progress", "done");

        if (structuredQuery.isEmpty() || !queryKeys.contains(structuredQuery.get(0))) {
            return false;
        }

        switch (structuredQuery.get(0)) {

            case "add" -> {
                if (structuredQuery.size() != 2) {
                    return false;
                }
                return structuredQuery.get(1).startsWith("\"") && structuredQuery.get(1).endsWith("\"");
            }

            case "update" -> {
                if (structuredQuery.size() != 3) {
                    return false;
                }
                try {
                    Integer.parseInt(structuredQuery.get(1));
                    return true;
                } catch (NumberFormatException e) {
                    return false;
                }
            }

            case "delete" -> {
                try {
                    Integer.parseInt(structuredQuery.get(1));
                    return true;
                } catch (NumberFormatException e) {
                    return false;
                }
            }

            case "mark" -> {
                if (structuredQuery.size() < 3) {
                    return false;
                }
                try {
                    Integer.parseInt(structuredQuery.get(1));
                    return status.contains(structuredQuery.get(2));
                } catch (NumberFormatException e) {
                    return false;
                }
            }

            case "list" -> {
                return structuredQuery.size() == 1 || (structuredQuery.size() == 2 && status.contains(structuredQuery.get(1)));
            }
        }

        return false;
    }

    private Task taskFinderHelper(int id) throws TaskNotFoundException {
        return taskList
                .stream()
                .filter(t -> t.getId() == id)
                .findAny()
                .orElseThrow(TaskNotFoundException::new);
    }

    public void processQuery(String query) throws TaskNotFoundException {
        List<String> taskQuery = queryOrganizer(query);

        if (!isQueryValid(query)) {
            System.out.println("Invalid Query. Please try again.");
            return;
        }
        switch (taskQuery.get(0)){

        case "add" -> {
            if (duplicateChecker(taskQuery.get(1))) {
                addTask(new Task(++idCounter, taskQuery.get(1).replace("\"", "")));
            } else {
                throw new TaskDuplicateException();
            }
        }
        case "delete" -> deleteTask(Integer.parseInt(taskQuery.get(1)));

        case "update" -> updateTask(Integer.parseInt(taskQuery.get(1)), taskQuery.get(2).replace("\"", ""));

        case "mark" -> updateProgress(Integer.parseInt(taskQuery.get(1)), taskQuery.get(2));

        case "list" -> {
            if (taskQuery.size() == 1) {
                listTasks();
            } else {
                listBasedOnProgress(taskQuery.get(1));
            }
        }

        case "exit" -> {
            System.out.println("Bye");
            System.exit(0);
        }
    }
}

private List<String> queryOrganizer(String query) {
    List<String> list = new ArrayList<>();
    Matcher m = Pattern.compile("([^\"]\\S*|\".+?\")\\s*").matcher(query);
    while (m.find()) {
        list.add(m.group(1));
    }
    return list;
}

private boolean duplicateChecker(String description) {
    return taskList.stream()
            .map(Task::getDescription)
            .noneMatch(description::equals);
}

public static class TaskNotFoundException extends RuntimeException {}

public static class TaskDuplicateException extends RuntimeException {}
}
