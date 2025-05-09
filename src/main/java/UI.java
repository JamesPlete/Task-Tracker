import java.util.Scanner;

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
        }
    }

//    public String process(String query){
//
//    }
}
