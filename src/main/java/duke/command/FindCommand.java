package duke.command;

import duke.Display;
import duke.Storage;
import duke.task.TaskList;

/**
 * Represents a Command that searches through all Task objects in the specified TaskList object for
 * Task objects where their taskName contains the specified substring identified in commandArgs
 */
public class FindCommand extends Command {
    /**
     * Runs the command to print a list of matching tasks in the TaskList object where each of the task's name
     * includes the specified substring as indicated in commandArgs.
     * 
     * @param ui      A Display object to manage printing of errors and other messages
     * @param tasks   A TaskList object with which the command may process or modify
     * @param storage A Storage object to manage the save file of the specified TaskList object if necessary
     */
    @Override
    public void run(Display ui, TaskList tasks, Storage storage) {
        if (commandArgs.trim().isEmpty()) {
            ui.printError(Display.ErrorType.MISSING_SUBSTRING);
            return;
        }
        String matchList = tasks.findTasksByString(commandArgs);
        ui.printlnMessage(matchList);
    }

    /**
     * Standard constructor, creates a FindCommand object with specified command arguments
     *
     * @param commandArgs a String object representing the substring to search for within the TaskList object
     */
    public FindCommand(String commandArgs) {
        super(commandArgs);
    }
}
