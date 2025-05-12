import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class Task {
    private final int id;
    private String description;
    private final String[] STATUS_LIST = {"to-do", "in-progress", "done"};
    private final LocalDateTime createdAt;
    private String status;
    private LocalDateTime updatedAt;

    public Task(int id, String description){
        this.id = id;
        this.description = description;
        this.status = STATUS_LIST[0];
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public String getStatus() {
        return status;
    }

    public String getDescription() {
        return description;
    }

    public int getId() {
        return id;
    }

    public LocalDateTime getLastUpdatedAt() {
        return updatedAt;
    }

    public void updateDescription(String description){
        this.description = description;
    }

    public void updateStatus(String status){
        this.status = status;
    }

    public void changeUpdateTime(){
        this.updatedAt = LocalDateTime.now();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj.getClass() != this.getClass()) return false;

        Task task = (Task) obj;
        return this.getId() == task.getId() && this.description.equals(task.description) && this.createdAt == task.createdAt;
    }

    public String getCreationTime(){
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy | HH:mm:ss");
        return createdAt.format(format);
    }


    @Override
    public String toString(){
        return id + ") " + description;
    }
}
