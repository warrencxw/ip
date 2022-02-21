package duke.command;

import duke.Display;
import duke.Storage;
import duke.task.TaskList;

public class ListCommand extends Command {
    @Override
    public void run(Display ui, TaskList tasks, Storage storage) {
        if (tasks.getSize() == 0) {
            ui.printError(Display.ErrorType.EMPTY_TASK_LIST);
            return;
        }
        ui.printlnMessage(tasks);
    }
}
