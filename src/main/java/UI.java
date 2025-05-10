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

            //Dito ka na, Boss !!!!, processing query naa
        }
    }

    private boolean isQueryValid(String query){
        String[] structuredQuery = query.split("\"[^\"]*\"|\\S+");
        List<String> queryKeys = List.of("add", "delete", "mark", "update", "list");
        List <String> status = List.of("to-do", "in progress", "done");

        if (!queryKeys.contains(structuredQuery[0])) return false;

        switch(structuredQuery[0]){
            case "add" -> {
                if (structuredQuery[1].charAt(0) != '"' && structuredQuery[1].endsWith("\"")) return false;
                return true;
            }

            case "update" -> {
                try {
                    int id = Integer.parseInt(structuredQuery[1]);
                    return true;
                } catch (NumberFormatException e){
                    return false;
                }
            }

            case "mark" ->{
                try {
                    int id = Integer.parseInt(structuredQuery[1]);
                    return status.contains(structuredQuery[2]);
                } catch (NumberFormatException e){
                    return false;
                }
            }


            case "delete" -> {
                try {
                    int id = Integer.parseInt(structuredQuery[1]);
                } catch (NumberFormatException e){
                    return false;
                }
            }

            case "list" -> {
                return status.contains(structuredQuery[1]);
            }
        }
        return false;
    }
}
