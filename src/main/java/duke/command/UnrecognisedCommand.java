package duke.command;

import duke.Display;
import duke.Storage;
import duke.task.TaskList;

/**
 * Represents a Command where the input is not recognised by the parser
 */
public class UnrecognisedCommand extends Command {
    /**
     * Runs the Command to print an error to indicate that the input was not recognised.
     *
     * @param ui      A Display object to manage printing of errors and other messages
     * @param tasks   A TaskList object with which the command may process or modify
     * @param storage A Storage object to manage the save file of the specified TaskList object if necessary
     */
    @Override
    public void run(Display ui, TaskList tasks, Storage storage) {
        ui.printError(Display.ErrorType.COMMAND_NOT_RECOGNISED);
    }
}
