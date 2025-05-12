import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.function.Predicate;

public class UI {
    TaskManager taskManager;
    Scanner scan;

    public UI(){
        taskManager = new TaskManager();
        scan = new Scanner(System.in);
    }

    public void run(){

        while (true){
            System.out.print("task-tracker-app: ");
            String query = scan.nextLine();

            try {
                taskManager.processQuery(query);
            } catch (TaskManager.TaskNotFoundException e){
                System.out.println("Task is not found in the list. Please try again.");
            }
        }
    }

}
