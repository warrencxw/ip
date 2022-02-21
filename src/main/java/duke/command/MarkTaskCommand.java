package duke.command;

import duke.Display;
import duke.DukeException;
import duke.Parser;
import duke.Storage;
import duke.task.TaskList;

public class MarkTaskCommand extends Command {
    // Member Attributes
    boolean toMark;
    
    /**
     * Modifies the completion of the task identified by the input task number and print out a confirmation message.
     * Will not modify tasks if taskNoString is not an integer, an error message is printed instead
     *
     * @param shouldMarkTask Determines whether to set task to done or not done
     * @param taskNoString   The input task number string that identifies the task in "tasks"
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
    
    @Override
    public void run(Display ui, TaskList tasks, Storage storage) {
        markTask(toMark, commandArgs, ui, tasks);
        storage.saveChanges(tasks);
    }
    
    public MarkTaskCommand(String commandArgs, boolean toMark) {
        super(commandArgs);
        this.toMark = toMark;
    }
}
