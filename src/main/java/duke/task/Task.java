package duke.task;

/**
 * Represents a generic task that possesses a name and a completion status
 */
public abstract class Task {
    // Member Variables
    protected String taskName;
    protected boolean isDone;

    /**
     * Default constructor, sets taskName to an empty String object and marks isDone to false.
     */
    public Task() {
        this("");
        this.isDone = false;
    }

    /**
     * Alternative constructor, sets taskName to a specified taskName and marks isDone to false.
     *
     * @param taskName a String object that specifies the name of the task
     */
    public Task(String taskName) {
        this.taskName = taskName;
        this.isDone = false;
    }

    /**
     * Custom constructor, sets taskName to a specified taskName and marks isDone as specified.
     * Used when loading tasks.
     *
     * @param taskName a String object that specifies the name of the task
     * @param isDone   the completion status of the Task
     * @see duke.Storage
     */
    public Task(String taskName, Boolean isDone) {
        this.taskName = taskName;
        this.isDone = isDone;
    }

    /**
     * Returns the name of the task
     *
     * @return String object containing name of task
     */
    public String getTaskName() {
        return taskName;
    }

    /**
     * Marks the completion status of the task according to whether the task is done or not
     *
     * @param done the completion status to which the task is changed to
     */
    public void setDone(boolean done) {
        isDone = done;
    }

    public boolean isDone() {
        return isDone;
    }

    /**
     * Prints a message to indicate that the task has been successfully added.
     *
     * @return a String object containing the success message
     */
    public abstract String getAddedMessage();

    /**
     * Convert Task object information into a savable CSV format
     *
     * @return a String object containing the CSV record representing the Task to be saved.
     */
    public abstract String getSavableCSVString();

    /**
     * Returns a String object containing all information of the task in a presentable format.
     *
     * @return a String object containing a readable representation of the task
     */
    @Override
    public String toString() {
        if (isDone) {
            return "[X] " + taskName;
        } else {
            return "[ ] " + taskName;
        }
    }
}