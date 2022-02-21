package duke.command;

import duke.Display;
import duke.DukeException;
import duke.Parser;
import duke.Storage;
import duke.task.Deadline;
import duke.task.TaskList;

import java.time.LocalDate;

public class DeadlineCommand extends CreateTaskCommand {
    @Override
    public void run(Display ui, TaskList tasks, Storage storage) {
        String[] taskDetailsString;
        LocalDate deadline;
        try {
            taskDetailsString = getTaskDetailTokens(commandArgs, TaskList.TaskType.DEADLINE, ui);
            deadline = Parser.parseDateFromString(taskDetailsString[1]);
        } catch (DukeException exception) {
            ui.printlnMessage(exception.getMessage());
            return;
        }

        Deadline newDeadline = new Deadline(taskDetailsString[0], deadline);
        tasks.addTask(newDeadline);
        ui.printlnMessage(newDeadline.getAddedMessage());
        storage.saveChanges(tasks);
    }

    public DeadlineCommand(String commandArgs) {
        super(commandArgs);
    }
}
