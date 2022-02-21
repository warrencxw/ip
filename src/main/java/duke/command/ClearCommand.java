package duke.command;

import duke.Display;
import duke.Storage;
import duke.task.TaskList;

/**
 * Represents the Command to clear all Task objects in the specified TaskList object
 */
public class ClearCommand extends Command {
    // Warning messages
    public static final String WARNING_DELETION =
            "Are you really sure you want to delete all available tasks? Type 'Y' to confirm.";
    public static final String TASK_DELETION_ABORTED =
            "Deletion has been aborted.";
    public static final String TASK_DELETED_CONFIRMATION =
            "All tasks have been cleared.";


    /**
     * Runs the command to clear all Task objects in the specified TaskList object
     * Warns the user and requests for a confirmation before the TaskList is completely cleared.
     * Saves all changes after deletion.
     *
     * @param ui      A Display object to manage printing of errors and other messages
     * @param tasks   A TaskList object with which the command may process or modify
     * @param storage A Storage object to manage the save file of the specified TaskList object if necessary
     */
    @Override
    public void run(Display ui, TaskList tasks, Storage storage) {
        ui.printlnMessage(WARNING_DELETION);
        String input = ui.getNextLine();
        if (!input.equalsIgnoreCase("Y")) {
            ui.printlnMessage(TASK_DELETION_ABORTED);
            return;
        }
        tasks.clearTasks();
        ui.printlnMessage(TASK_DELETED_CONFIRMATION);
        storage.saveChanges(tasks);
    }
}
