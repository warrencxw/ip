package duke.command;

import duke.Display;
import duke.Storage;
import duke.task.TaskList;
import duke.task.Todo;

/**
 * Represents a Command that creates a Todo object to be added into the specified TaskList object
 */
public class TodoCommand extends CreateTaskCommand {
    /**
     * Runs the command to create a Todo object using the input arguments specified in commandArgs and
     * add the object into the TaskList object. Saves all changes after insertion.
     *
     * @param ui      A Display object to manage printing of errors and other messages
     * @param tasks   A TaskList object with which the command may process or modify
     * @param storage A Storage object to manage the save file of the specified TaskList object if necessary
     */
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

    /**
     * Standard constructor, creates a TodoCommand object with specified command arguments
     *
     * @param commandArgs a String object representing the task name of the Todo object
     */
    public TodoCommand(String commandArgs) {
        super(commandArgs);
    }
}
