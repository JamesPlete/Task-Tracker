package view;

import controller.TaskController;
import controller.TaskControllerUtility;

import java.util.Scanner;

import static controller.TaskControllerUtility.*;

public class TaskView {
    private static TaskController taskController;
    private static Scanner scan;

    public TaskView() {
        taskController = new TaskController();
        scan = new Scanner(System.in);
    }

    public static void main(String[] args) {
        TaskView ui = new TaskView();
        ui.run();
    }

    public void run() {
        String query = "";
        System.out.println();

        while (!query.equals("exit")) {
            System.out.print("task-tracker-app: ");
            query = scan.nextLine();

            try {
                processQuery(query, taskController);
            } catch (TaskControllerUtility.TaskNotFoundException e) {
                System.out.println("Task is not found in the list. Please try again.");
            } catch (TaskControllerUtility.TaskDuplicateException e) {
                System.out.println("Error. Task is already in the database.");
            }
        }
    }
}
