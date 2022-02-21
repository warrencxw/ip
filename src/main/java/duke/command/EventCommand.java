package duke.command;

import duke.Display;
import duke.DukeException;
import duke.Parser;
import duke.Storage;
import duke.task.Event;
import duke.task.TaskList;

import java.time.LocalDate;

public class EventCommand extends CreateTaskCommand {
    @Override
    public void run(Display ui, TaskList tasks, Storage storage) {
        String[] taskDetailsString;
        LocalDate eventTime;
        try {
            taskDetailsString = getTaskDetailTokens(commandArgs, TaskList.TaskType.EVENT, ui);
            eventTime = Parser.parseDateFromString(taskDetailsString[1]);
        } catch (DukeException exception) {
            ui.printlnMessage(exception.getMessage());
            return;
        }

        Event newEvent = new Event(taskDetailsString[0], eventTime);
        tasks.addTask(newEvent);
        ui.printlnMessage(newEvent.getAddedMessage());
        storage.saveChanges(tasks);
    }

    public EventCommand(String commandArgs) {
        super(commandArgs);
    }
}
