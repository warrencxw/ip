package duke.task;

public abstract class Task {
    // Member Variables
    protected String taskName;
    protected boolean isDone;

    public Task() {
        this("");
        this.isDone = false;
    }

    public Task(String taskName) {
        this.taskName = taskName;
        this.isDone = false;
    }
    
    public Task(String taskName, Boolean isDone) {
        this.taskName = taskName;
        this.isDone = isDone;
    } 

    public String getTaskName() {
        return taskName;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    public boolean isDone() {
        return isDone;
    }

    public abstract void printAddedMessage();
    
    public abstract String getSavableCSVString();

    @Override
    public String toString() {
        if (isDone) {
            return "[X] " + taskName;
        } else {
            return "[ ] " + taskName;
        }
    }
}