package duke.command;

import duke.Display;
import duke.Storage;
import duke.task.TaskList;
import duke.task.Todo;

public class TodoCommand extends CreateTaskCommand {
    
    @Override
    public void run(Display ui, TaskList tasks, Storage storage) {
        if (commandArgs.trim().isEmpty()) {
            ui.printError(Display.ErrorType.EMPTY_TASK_NAME);
            return;
        }
        Todo newTodo = new Todo(commandArgs);
        tasks.addTask(newTodo);
        ui.printlnMessage(newTodo.getAddedMessage());
        storage.saveChanges(tasks);
    }
    
    public TodoCommand(String commandArgs) {
        super(commandArgs);
    }
}
