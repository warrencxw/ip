package duke.command;

import duke.Display;
import duke.DukeException;
import duke.Storage;
import duke.task.Event;
import duke.task.TaskList;

public class EventCommand extends CreateTaskCommand {
    @Override
    public void run(Display ui, TaskList tasks, Storage storage) {
        String[] taskDetailsString;
        try {
            taskDetailsString = getTaskDetailTokens(commandArgs, TaskList.TaskType.EVENT, ui);
        } catch (DukeException exception) {
            ui.printlnMessage(exception.getMessage());
            return;
        }
        
        Event newEvent = new Event(taskDetailsString[0], taskDetailsString[1]);
        tasks.addTask(newEvent);
        ui.printlnMessage(newEvent.getAddedMessage());
        storage.saveChanges(tasks);
    }

    public EventCommand(String commandArgs) {
        super(commandArgs);
    }
}
