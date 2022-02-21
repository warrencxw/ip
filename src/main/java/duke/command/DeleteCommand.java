package duke.command;

import duke.Display;
import duke.DukeException;
import duke.Parser;
import duke.Storage;
import duke.task.TaskList;

/**
 * Represents a Command that deletes a Task object in the specified TaskList object indicated by the specified
 * task number of the task.
 */
public class DeleteCommand extends Command {
    /**
     * Checks if there are arguments in the 'delete' command and if the arguments are valid (integer and within
     * range of [1, tasks.size()]). If arguments are valid, deletes the task identified by the argument and prints
     * a success message onto standard output. Prints an error message and does not modify tasks otherwise.
     *
     * @param taskNoString A String object containing the task number indicating the Task object to be deleted
     * @param ui           A Display object to manage printing of errors and other messages
     * @param tasks        A TaskList object in which the specified task is deleted
     */
    public void processInputAndDeleteTask(String taskNoString, Display ui, TaskList tasks) {
        int taskNo;
        try {
            taskNo = Parser.parseTaskNoFromString(taskNoString, ui, tasks);
        } catch (DukeException exception) {
            ui.printlnMessage(exception.getMessage());
            return;
        }

        try {
            String successMessage =
                    "I have removed the following task for you:\n"
                            + "> " + (taskNo + 1) + ". " + tasks.getTask(taskNo).toString() + "\n"
                            + "You now have " + (tasks.getSize() - 1) + " tasks in your list.";
            tasks.removeTask(taskNo);
            ui.printlnMessage(successMessage);
        } catch (IndexOutOfBoundsException exception) {
            // Should never occur, Parser::parseTaskNoFromString will check for bounds
            exception.printStackTrace();
        }
    }

    /**
     * Runs the command to delete a Task object in the specified TaskList object, specified by the
     * task number indicated in commandArgs. Saves all changes after deletion.
     *
     * @param ui      A Display object to manage printing of errors and other messages
     * @param tasks   A TaskList object with which the command may process or modify
     * @param storage A Storage object to manage the save file of the specified TaskList object if necessary
     */
    @Override
    public void run(Display ui, TaskList tasks, Storage storage) {
        processInputAndDeleteTask(commandArgs, ui, tasks);
        storage.saveChanges(tasks);
    }

    /**
     * Standard constructor, creates a DeleteCommand object with specified command arguments
     *
     * @param commandArgs a String object representing the task number that identifies the Task to be deleted
     */
    public DeleteCommand(String commandArgs) {
        super(commandArgs);
    }
}
