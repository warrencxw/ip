package duke.task;

import duke.Duke;

/**
 * Represents an event or an activity that happened or will happen at a specific time.
 */
public class Event extends Task {
    protected String eventTime;

    /**
     * Default constructor, sets taskName to an empty String object and marks isDone to false.
     * Additionally, sets eventTime to an empty String object
     */
    public Event() {
        super();
        this.eventTime = "";
    }

    /**
     * Alternative constructor, sets taskName to a specified taskName and marks isDone to false.
     * Additionally, specifies the eventTime of the Event object according to the eventTime specified.
     *
     * @param taskName  a String object that specifies the name of the task
     * @param eventTime the time at which the event occurs
     */
    public Event(String taskName, String eventTime) {
        super(taskName);
        this.eventTime = eventTime;
    }

    /**
     * Custom constructor, sets taskName to a specified taskName and marks isDone as specified.
     * Additionally, specifies the eventTime of the Event object according to the eventTime specified.
     * Used when loading tasks.
     *
     * @param taskName  a String object that specifies the name of the task
     * @param isDone    the completion status of the Task
     * @param eventTime the time at which the event occurs
     */
    public Event(String taskName, Boolean isDone, String eventTime) {
        super(taskName, isDone);
        this.eventTime = eventTime;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (happening at: " + eventTime + ")";
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
