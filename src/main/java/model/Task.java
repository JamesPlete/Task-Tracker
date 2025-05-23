package model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public final class Task {

    @JsonProperty("id")
    private int id;
    @JsonProperty("description")
    private String description;
    @JsonProperty("status")
    private Status status;
    @JsonProperty("createdAt")
    private String createdAt;
    @JsonProperty("updatedAt")
    private String updatedAt;

    public Task() {}

    public Task(int id, String description) {
        this.id = id;
        this.description = description;
        this.status = Status.TODO;
        this.createdAt = timeFormatter(LocalDateTime.now());
        this.updatedAt = timeFormatter(LocalDateTime.now());
    }

    private String timeFormatter(LocalDateTime dateTime) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy | HH:mm:ss");
        return format.format(dateTime);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public String getStatus() {
        return status.getStatus();
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void updateDescription(String description) {
        this.description = description;
    }

    public void updateStatus(Status status) {
        this.status = status;
    }

    public void changeUpdateTime() {
        this.updatedAt = timeFormatter(LocalDateTime.now());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Task task)) return false;
        return this.getId() == task.getId()
                && this.description.equals(task.description)
                && this.createdAt.equals(task.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getDescription(), getStatus(), createdAt, updatedAt);
    }

    @Override
    public String toString() {
        return id + ") " + description;
    }
}
