package duke.task;

public class Todo extends Task {
    public Todo() {
        super();
    }

    public Todo(String taskName) {
        super(taskName);
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
}
