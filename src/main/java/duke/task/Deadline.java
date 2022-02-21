package duke.task;

import duke.Duke;

import java.time.LocalDate;

public class Deadline extends Task {
    protected LocalDate dueDate;

    public Deadline() {
        super();
        this.dueDate = LocalDate.now();
    }

    public Deadline(String taskName, LocalDate dueDate) {
        super(taskName);
        this.dueDate = dueDate;
    }

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
