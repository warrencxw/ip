package duke.task;

import duke.Duke;

import java.time.LocalDate;

public class Event extends Task {
    protected LocalDate eventTime;

    public Event() {
        super();
        this.eventTime = LocalDate.now();
    }

    public Event(String taskName, LocalDate eventTime) {
        super(taskName);
        this.eventTime = eventTime;
    }

    public Event(String taskName, Boolean isDone, LocalDate eventTime) {
        super(taskName, isDone);
        this.eventTime = eventTime;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (happening at: " 
                + eventTime.getDayOfMonth() + " " + eventTime.getMonth() + " " + eventTime.getYear() + ")";
    }

    @Override
    public String getAddedMessage() {
        return "Event \"" + taskName + "\" has been added.\n"
                + " > " + toString();
    }

    @Override
    public String getSavableCSVString() {
        return "E" + Duke.CSV_DELIMITER
                + (isDone ? "Y" : "N") + Duke.CSV_DELIMITER
                + taskName + Duke.CSV_DELIMITER
                + eventTime;
    }
}
