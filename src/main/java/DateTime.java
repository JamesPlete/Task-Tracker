import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTime {
    private LocalDateTime dateTime;

    public DateTime (){
        dateTime = LocalDateTime.now();
    }

    public void changeUpdateTime(){
        dateTime = LocalDateTime.now();
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy | HH:mm:ss");
        return dateTime.format(formatter);
    }

}
