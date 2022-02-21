package duke.command;

import duke.Display;
import duke.Storage;
import duke.task.TaskList;

public class UnrecognisedCommand extends Command {
    @Override
    public void run(Display ui, TaskList tasks, Storage storage) {
        ui.printError(Display.ErrorType.COMMAND_NOT_RECOGNISED);
    }
}
