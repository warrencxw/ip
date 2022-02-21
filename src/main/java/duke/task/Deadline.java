package duke.task;

import duke.Duke;

import java.time.LocalDate;

/**
 * Represents a task with a deadline that may or may not have passed.
 */
public class Deadline extends Task {
    protected LocalDate dueDate;

    /**
     * Default constructor, sets taskName to an empty String object and marks isDone to false.
     * Additionally, sets dueDate to an empty String object
     */
    public Deadline() {
        super();
        this.dueDate = LocalDate.now();
    }

    /**
     * Alternative constructor, sets taskName to a specified taskName and marks isDone to false.
     * Additionally, specifies the dueDate of the Deadline object according to the dueDate specified.
     *
     * @param taskName a String object that specifies the name of the task
     * @param dueDate  the time by which the task is due
     */
    public Deadline(String taskName, LocalDate dueDate) {
        super(taskName);
        this.dueDate = dueDate;
    }

    /**
     * Custom constructor, sets taskName to a specified taskName and marks isDone as specified.
     * Additionally, specifies the dueDate of the Deadline object according to the dueDate specified.
     * Used when loading tasks.
     *
     * @param taskName a String object that specifies the name of the task
     * @param isDone   the completion status of the Task
     * @param dueDate  the time by which the task is due
     */
    public Deadline(String taskName, Boolean isDone, LocalDate dueDate) {
        super(taskName, isDone);
        this.dueDate = dueDate;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (due: " + 
                dueDate.getDayOfMonth() + " " + dueDate.getMonth() + " " + dueDate.getYear() + ")";
    }

    @Override
    public String getAddedMessage() {
        return "Deadline \"" + taskName + "\" has been added.\n"
                + " > " + toString();
    }

    @Override
    public String getSavableCSVString() {
        return "D" + Duke.CSV_DELIMITER
                + (isDone ? "Y" : "N") + Duke.CSV_DELIMITER
                + taskName + Duke.CSV_DELIMITER
                + dueDate;
    }
}
