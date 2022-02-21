package duke.command;

import duke.Display;
import duke.DukeException;
import duke.Parser;
import duke.Storage;
import duke.task.Event;
import duke.task.TaskList;

import java.time.LocalDate;

/**
 * Represents a Command that creates a Event object to be added into the specified TaskList object
 */
public class EventCommand extends CreateTaskCommand {
    /**
     * Runs the command to create a Event object using the input arguments specified in commandArgs and
     * add the object into the TaskList object. Saves all changes after insertion.
     *
     * @param ui      A Display object to manage printing of errors and other messages
     * @param tasks   A TaskList object with which the command may process or modify
     * @param storage A Storage object to manage the save file of the specified TaskList object if necessary
     */
    @Override
    public void run(Display ui, TaskList tasks, Storage storage) {
        String[] taskDetailsString;
        LocalDate eventTime;
        try {
            taskDetailsString = getTaskDetailTokens(commandArgs, TaskList.TaskType.EVENT, ui);
            eventTime = Parser.parseDateFromString(taskDetailsString[1]);
        } catch (DukeException exception) {
            ui.printlnMessage(exception.getMessage());
            return;
        }

        Event newEvent = new Event(taskDetailsString[0], eventTime);
        tasks.addTask(newEvent);
        ui.printlnMessage(newEvent.getAddedMessage());
        storage.saveChanges(tasks);
    }

    /**
     * Standard constructor, creates a EventCommand object with specified command arguments
     *
     * @param commandArgs a String object representing the task name and the event date of the Event object
     */
    public EventCommand(String commandArgs) {
        super(commandArgs);
    }
}
