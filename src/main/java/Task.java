public final class Task {
    private final int id;
    private String description;
    private Status status;
    private final DateTime createdAt;
    private DateTime updatedAt;

    public Task(int id, String description){
        this.id = id;
        this.description = description;
        this.status = Status.TODO;
        this.createdAt = new DateTime();
        this.updatedAt = new DateTime();
    }

    public DateTime getCreationTime(){
        return createdAt;
    }

    public Status getStatus() {
        return status;
    }

    public int getId() {
        return id;
    }

    public void updateDescription(String description){
        this.description = description;
    }

    public void updateStatus(Status status){
        this.status = status;
    }

    public void changeUpdateTime(){
        this.updatedAt.changeUpdateTime();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;

        Task task = (Task) obj;

        return this.getId() == task.getId() && this.description.equals(task.description) && this.createdAt == task.createdAt;
    }

    @Override
    public String toString(){
        return id + ") " + description;
    }
}
