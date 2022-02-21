package duke.command;

import duke.Display;
import duke.DukeException;
import duke.Storage;
import duke.task.Deadline;
import duke.task.TaskList;

public class DeadlineCommand extends CreateTaskCommand {
    @Override
    public void run(Display ui, TaskList tasks, Storage storage) {
        String[] taskDetailsString;
        try {
            taskDetailsString = getTaskDetailTokens(commandArgs, TaskList.TaskType.DEADLINE, ui);
        } catch (DukeException exception) {
            ui.printlnMessage(exception.getMessage());
            return;
        }

        Deadline newDeadline = new Deadline(taskDetailsString[0], taskDetailsString[1]);
        tasks.addTask(newDeadline);
        ui.printlnMessage(newDeadline.getAddedMessage());
        storage.saveChanges(tasks);
    }

    public DeadlineCommand(String commandArgs) {
        super(commandArgs);
    }
}
