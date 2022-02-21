package duke.command;

import duke.Display;
import duke.DukeException;
import duke.Parser;
import duke.Storage;
import duke.task.TaskList;

public class DeleteCommand extends Command {
    /**
     * Checks if there are arguments in the 'delete' command and if the arguments are valid (integer and within
     * range of [1, tasks.size()]). If arguments are valid, deletes the task identified by the argument and prints
     * a success message onto standard output. Prints an error message and does not modify tasks otherwise.
     *
     * @param inputs List of input tokens provided by the user
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
            // Should never occur, Parser::parseTaskNoFromString will check for bounds.
            exception.printStackTrace();
        }
    }

    @Override
    public void run(Display ui, TaskList tasks, Storage storage) {
        processInputAndDeleteTask(commandArgs, ui, tasks);
        storage.saveChanges(tasks);
    }

    public DeleteCommand(String commandArgs) {
        super(commandArgs);
    }
}
