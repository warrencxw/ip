package duke.task;

import duke.Duke;

public class Deadline extends Task {
    protected String dueDate;

    public Deadline() {
        super();
        this.dueDate = "";
    }

    public Deadline(String taskName, String dueDate) {
        super(taskName);
        this.dueDate = dueDate;
    }
    
    public Deadline(String taskName, Boolean isDone, String dueDate) {
        super(taskName, isDone);
        this.dueDate = dueDate;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (due: " + dueDate + ")";
    }

    @Override
    public void printAddedMessage() {
        System.out.println("Deadline \"" + taskName + "\" has been added.");
        System.out.println(" > " + toString());
    }

    @Override
    public String getSavableCSVString() {
        return "D" + Duke.CSV_DELIMITER
                + (isDone ? "Y" : "N") + Duke.CSV_DELIMITER
                + taskName + Duke.CSV_DELIMITER
                + dueDate;
    }
}
