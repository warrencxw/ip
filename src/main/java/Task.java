public class Task {
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

    public String getTaskName() {
        return taskName;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    public boolean isDone() {
        return isDone;
    }

    public void printAddedMessage() {
        System.out.println("Task \"" + taskName + "\" has been added.");
        System.out.println(" > " + toString());
    }

    @Override
    public String toString() {
        if (isDone) {
            return "[X] " + taskName;
        } else {
            return "[ ] " + taskName;
        }
    }
}