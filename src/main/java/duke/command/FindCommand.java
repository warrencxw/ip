package duke.command;

import duke.Display;
import duke.Storage;
import duke.task.TaskList;

public class FindCommand extends Command {
    @Override
    public void run(Display ui, TaskList tasks, Storage storage) {
        if (commandArgs.trim().isEmpty()) {
            ui.printError(Display.ErrorType.MISSING_SUBSTRING);
            return;
        }
        String matchList = tasks.findTasksByString(commandArgs);
        ui.printlnMessage(matchList);
    }
    
    public FindCommand(String commandArgs) {
        super(commandArgs);
    }
}
