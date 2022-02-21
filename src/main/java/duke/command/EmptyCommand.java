package duke.command;

import duke.Display;
import duke.Storage;
import duke.task.TaskList;

public class EmptyCommand extends Command {
    @Override
    public void run(Display ui, TaskList tasks, Storage storage) {
        ui.printError(Display.ErrorType.EMPTY_INPUT);
    }
}
