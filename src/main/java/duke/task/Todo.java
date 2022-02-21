package duke.task;

import duke.Duke;

/**
 * Represents a generic task that has to be done, regardless of time
 */
public class Todo extends Task {
    /**
     * Default constructor, sets taskName to an empty String object and marks isDone to false.
     */
    public Todo() {
        super();
    }

    /**
     * Alternative constructor, sets taskName to a specified taskName and marks isDone to false.
     *
     * @param taskName a String object that specifies the name of the task
     */
    public Todo(String taskName) {
        super(taskName);
    }

    /**
     * Custom constructor, sets taskName to a specified taskName and marks isDone as specified.
     * Used when loading tasks.
     *
     * @param taskName a String object that specifies the name of the task
     * @param isDone   the completion status of the Task
     * @see duke.Storage
     */
    public Todo(String taskName, Boolean isDone) {
        super(taskName, isDone);
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }

    @Override
    public String getAddedMessage() {
        return "Todo \"" + taskName + "\" has been added.\n"
                + " > " + toString();
    }

    @Override
    public String getSavableCSVString() {
        return "T" + Duke.CSV_DELIMITER
                + (isDone ? "Y" : "N") + Duke.CSV_DELIMITER
                + taskName;
    }
}
