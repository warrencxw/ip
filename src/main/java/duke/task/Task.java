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
    
    public boolean containsString(String substring) {
        return taskName.contains(substring);
    }

    /**
     * Prints a message to indicate that the task has been successfully added.
     * @return
     */
    public abstract String getAddedMessage();

    /**
     * Convert Task object information into a savable CSV format
     * 
     * @return Returns a String object containing the CSV record representing the Task to be saved.
     */
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