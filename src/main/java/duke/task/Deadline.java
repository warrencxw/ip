package duke.task;

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

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (due: " + dueDate + ")";
    }

    @Override
    public void printAddedMessage() {
        System.out.println("Deadline \"" + taskName + "\" has been added.");
        System.out.println(" > " + toString());
    }
}
