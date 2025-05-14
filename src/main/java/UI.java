import java.util.Scanner;

public class UI {
    TaskManager taskManager;
    Scanner scan;

    public UI() {
        taskManager = new TaskManager();
        scan = new Scanner(System.in);
    }

    public void run() {

        while (true) {
            System.out.print("task-tracker-app: ");
            String query = scan.nextLine();

            try {
                taskManager.processQuery(query);
              
            } catch (TaskManager.TaskNotFoundException e) {
                System.out.println("Task is not found in the list. Please try again.");
            } catch (TaskManager.TaskDuplicateException e){
                System.out.println("Duplicated Task. Task description is a already in the database.");
            }
        }
    }
  
}