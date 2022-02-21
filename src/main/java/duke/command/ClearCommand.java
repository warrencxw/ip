package duke.command;

import duke.Display;
import duke.Storage;
import duke.task.TaskList;

public class ClearCommand extends Command {
    // Warning messages
    public static final String WARNING_DELETION =
            "Are you really sure you want to delete all available tasks? Type 'Y' to confirm.";
    public static final String TASK_DELETION_ABORTED =
            "Deletion has been aborted.";
    public static final String TASK_DELETED_CONFIRMATION =
            "All tasks have been cleared.";


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
