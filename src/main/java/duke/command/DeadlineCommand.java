package duke.command;

import duke.Display;
import duke.DukeException;
import duke.Storage;
import duke.task.Deadline;
import duke.task.TaskList;

/**
 * Represents a Command that creates a Deadline object to be added into the specified TaskList object
 */
public class DeadlineCommand extends CreateTaskCommand {
    /**
     * Runs the command to create a Deadline object using the input arguments specified in commandArgs and
     * add the object into the TaskList object. Saves all changes after insertion.
     *
     * @param ui      A Display object to manage printing of errors and other messages
     * @param tasks   A TaskList object with which the command may process or modify
     * @param storage A Storage object to manage the save file of the specified TaskList object if necessary
     */
    @Override
    public void run(Display ui, TaskList tasks, Storage storage) {
        String[] taskDetailsString;
        try {
            taskDetailsString = getTaskDetailTokens(commandArgs, TaskList.TaskType.DEADLINE, ui);
        } catch (DukeException exception) {
            ui.printlnMessage(exception.getMessage());
            return;
        }

        Deadline newDeadline = new Deadline(taskDetailsString[0], taskDetailsString[1]);
        tasks.addTask(newDeadline);
        ui.printlnMessage(newDeadline.getAddedMessage());
        storage.saveChanges(tasks);
    }

    /**
     * Standard constructor, creates a DeadlineCommand object with specified command arguments
     *
     * @param commandArgs a String object representing the task name and the due date of the Deadline object
     */
    public DeadlineCommand(String commandArgs) {
        super(commandArgs);
    }
}
