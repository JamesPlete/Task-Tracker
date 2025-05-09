public enum Status {
    TODO("To-Do"),
    DOING("In progress"),
    DONE("Done");

    private String status;

    Status(String status){
      this.status = status;
     }

     public String getStatus(){
        return status;
     }
}
