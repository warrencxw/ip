package duke.command;

import duke.Display;
import duke.DukeException;
import duke.Parser;
import duke.Storage;
import duke.task.TaskList;

/**
 * Represents a Command that marks a Task object in the specified TaskList object indicated by the specified
 * task number of the task as either completed or not, as specified.
 */
public class MarkTaskCommand extends Command {
    // Member Attributes
    boolean done;

    /**
     * Modifies the completion of the task identified by the input task number and print out a confirmation message.
     * Will not modify tasks if taskNoString is not an integer; an error message is printed instead
     *
     * @param shouldMarkTask Determines whether to set task to done or not done
     * @param taskNoString   The input task number string that identifies the task in "tasks"
     * @param ui             A Display object to manage printing of errors and other messages
     * @param tasks          A TaskList object in which the specified task is deleted
     */
    private void markTask(boolean shouldMarkTask, String taskNoString, Display ui, TaskList tasks) {
        // Check if task number is numerical
        int taskNo;
        try {
            taskNo = Parser.parseTaskNoFromString(taskNoString, ui, tasks);
        } catch (DukeException exception) {
            ui.printlnMessage(exception.getMessage());
            return;
        }

        // Check if already marked / unmarked
        try {
            if (tasks.getTask(taskNo).isDone() == shouldMarkTask) {
                ui.printlnMessage("Your task has already been marked as " + (shouldMarkTask ? "done!" : "not done!"));
            } else {
                tasks.getTask(taskNo).setDone(shouldMarkTask);
                ui.printlnMessage("Your task is now marked as " + (shouldMarkTask ? "done!" : "not done!"));
            }
            ui.printlnMessage("> " + (taskNo + 1) + ". " + tasks.getTask(taskNo).toString());
        } catch (IndexOutOfBoundsException exception) {
            // Should never occur, Parser::parseTaskNoFromString will check for bounds.
            exception.printStackTrace();
        }
    }

    /**
     * Runs the command to mark the Task object in the TaskList object as indicated by the task number in the
     * commandArgs as done or not yet done.
     *
     * @param ui      A Display object to manage printing of errors and other messages
     * @param tasks   A TaskList object with which the command may process or modify
     * @param storage A Storage object to manage the save file of the specified TaskList object if necessary
     */
    @Override
    public void run(Display ui, TaskList tasks, Storage storage) {
        markTask(done, commandArgs, ui, tasks);
        storage.saveChanges(tasks);
    }

    /**
     * Standard constructor, creates a EventCommand object with specified command arguments
     *
     * @param commandArgs A String object representing the task name and the event date of the Event object
     * @param done        indicates whether the Task should be marked as complete or not
     */
    public MarkTaskCommand(String commandArgs, boolean done) {
        super(commandArgs);
        this.done = done;
    }
}
