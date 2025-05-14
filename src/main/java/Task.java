import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Objects;

public final class Task {
    private final int id;
    private String description;
    private final String[] STATES = {"to-do", "in-progress", "done"};
    private String status;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Task(int id, String description){
        this.id = id;
        this.description = description;
        this.status = STATES[0];
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public String getCreationTime(){
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy | HH:mm:ss");
        return createdAt.format(format);
    }

    public String getStatus() {
        return status;
    }

    public int getId() {
        return id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getDescription(), Arrays.hashCode(STATES), getStatus(), createdAt, updatedAt);
    }

    public String getDescription() {
        return description;
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

    @Override
    public String toString(){
        return id + ") " + description;
    }
}
