public class Task {
    private String taskName;
    private boolean isDone;

    public Task() {
        this("");
    }

    public Task(String taskName) {
        this.taskName = taskName;
        this.isDone = false;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    public String getTaskName() {
        return taskName;
    }

    public boolean getIsDone() {
        return isDone;
    }

    public String toPrint() {
        if (isDone) {
            return "[X] " + taskName;
        }
        else {
            return "[ ] " + taskName;
        }
    }
}