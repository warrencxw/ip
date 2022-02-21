package duke.command;

import duke.Display;
import duke.Storage;
import duke.task.TaskList;

/**
 * Represents a Command that shows the entire list of Task objects in the specified TaskList object to the user
 */
public class ListCommand extends Command {
    /**
     * Runs the command to print the entire TaskList for the user.
     * Prints an error message instead, if the TaskList object is empty.
     *
     * @param ui      A Display object to manage printing of errors and other messages
     * @param tasks   A TaskList object with which the command may process or modify
     * @param storage A Storage object to manage the save file of the specified TaskList object if necessary
     */
    @Override
    public void run(Display ui, TaskList tasks, Storage storage) {
        if (tasks.getSize() == 0) {
            ui.printError(Display.ErrorType.EMPTY_TASK_LIST);
            return;
        }
        ui.printlnMessage(tasks);
    }
}
