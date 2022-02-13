package duke.task;

import duke.Duke;

public class Todo extends Task {
    public Todo() {
        super();
    }

    public Todo(String taskName) {
        super(taskName);
    }
    
    public Todo(String taskName, Boolean isDone) {
        super(taskName, isDone);
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }

    @Override
    public void printAddedMessage() {
        System.out.println("Todo \"" + taskName + "\" has been added.");
        System.out.println(" > " + toString());
    }

    @Override
    public String getSavableCSVString() {
        return "T" + Duke.CSV_DELIMITER
                + (isDone ? "Y" : "N") + Duke.CSV_DELIMITER
                + taskName;
    }
}
