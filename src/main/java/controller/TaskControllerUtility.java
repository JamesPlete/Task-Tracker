package controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.Task;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TaskControllerUtility {

    public static List<Task> getAllTasks(ObjectMapper mapper, File jsonFile) {
        try {
            return mapper.readValue(jsonFile, new TypeReference<List<Task>>() {});
        } catch (IOException e) {
            System.out.println("Error occurred: " + e.getMessage());
        }
        return new ArrayList<>();
    }

    public static void verifyFile(File jsonFile) {
        if (!jsonFile.exists()) {
            try {
                //noinspection ResultOfMethodCallIgnored
                jsonFile.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void saveToFile(List<Task> taskList, ObjectMapper mapper, File jsonFile) {
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(jsonFile, taskList);
        } catch (IOException e) {
            System.out.println("\nError occurred: " + e.getMessage());
        }
    }

    public static void processQuery(String query, TaskController taskController) throws TaskNotFoundException {
        List<String> taskQuery = queryOrganizer(query);

        if (!isQueryValid(query)) {
            System.out.println("Invalid Query. Please try again.");
            return;
        }

        switch (taskQuery.get(0)) {
            case "add" -> {
                taskController.addTask(new Task(1, taskQuery.get(1).replace("\"", "")));
                System.out.println("Task added successfully.");
            }
            case "delete" -> taskController.deleteTask(Integer.parseInt(taskQuery.get(1)));
            case "update" -> taskController.updateTask(Integer.parseInt(taskQuery.get(1)), taskQuery.get(2).replace("\"", ""));
            case "mark" -> taskController.updateProgress(Integer.parseInt(taskQuery.get(1)), taskQuery.get(2));
            case "list" -> {
                if (taskQuery.size() == 1) {
                    taskController.listTasks();
                } else {
                    taskController.listBasedOnProgress(taskQuery.get(1));
                }
            }
            case "exit" -> {
                System.out.println("Bye");
                System.exit(0);
            }
        }
    }

    public static boolean isQueryValid(String query) {
        List<String> structuredQuery = queryOrganizer(query);
        List<String> queryKeys = List.of("add", "delete", "mark", "update", "list", "exit");
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
            case "exit" -> {
                return structuredQuery.size() == 1;
            }
        }
        return false;
    }

    public static List<String> queryOrganizer(String query) {
        List<String> list = new LinkedList<>();
        Matcher m = Pattern.compile("([^\"]\\S*|\".+?\")\\s*").matcher(query);

        while (m.find()) {
            list.add(m.group(1));
        }
        return list;
    }

    public static Task taskFinder(int id, List<Task> taskList) throws TaskNotFoundException {
        return taskList.stream()
                .filter(t -> t.getId() == id)
                .findAny()
                .orElseThrow(TaskNotFoundException::new);
    }

    public static void adjustList(int id, List<Task> taskList) {
        taskList.stream()
                .filter(t -> t.getId() > id)
                .forEach(t -> t.setId(t.getId() - 1));
    }

    public static class TaskNotFoundException extends RuntimeException {}

    public static class TaskDuplicateException extends RuntimeException {}
}
