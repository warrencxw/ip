package duke.task;

import duke.Duke;

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
    
    public Event(String taskName, Boolean isDone, String eventTime) {
        super(taskName, isDone);
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

    @Override
    public String getSavableCSVString() {
        return "E" + Duke.CSV_DELIMITER 
                + (isDone ? "Y" : "N") + Duke.CSV_DELIMITER 
                + taskName + Duke.CSV_DELIMITER 
                + eventTime;
    }
}
