package duke.task;

public class Event extends Task {
    protected String eventTime;

    public Event() {
        super();
        this.eventTime = "";
    }

    public Event(String taskName, String eventTime) {
        super(taskName);
        this.eventTime = eventTime;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (happening at: " + eventTime + ")";
    }

    @Override
    public void printAddedMessage() {
        System.out.println("Event \"" + taskName + "\" has been added.");
        System.out.println(" > " + toString());
    }
}
