package duke.command;

import duke.Display;
import duke.DukeException;
import duke.task.TaskList;

/**
 * Represents a generic Command that creates a Task object to be added into the specified TaskList object
 */
public abstract class CreateTaskCommand extends Command {
    /**
     * Checks whether the input argument string is empty or missing a task name for Event and Deadline classes.
     *
     * @param taskDetailString Input string that follows the syntax [task name] [delimiter] [time specification]
     * @param taskType         Either TaskType.EVENT or TaskType.DEADLINE
     * @return Returns true if taskDetailString starts with the delimiter, i.e. task name is missing,
     * returns false otherwise.
     * @throws DukeException If taskType is NOT TaskType.EVENT or TaskType.DEADLINE
     */
    private boolean isDelimiterWithoutTaskName(String taskDetailString, TaskList.TaskType taskType, Display ui) 
            throws DukeException {
        String stringPattern = TaskList.getStringPatternOfTask(taskType);
        boolean isEmptyArgument = taskDetailString.isEmpty();
        boolean isMissingTaskName = taskDetailString.indexOf(stringPattern) == 0;
        return isEmptyArgument || isMissingTaskName;
    }

    /**
     * Takes in an input string and returns a String array of {task name, time specification} if input syntax is valid,
     * for Event and Deadline objects.
     *
     * @param taskDetailString Input string that follows the syntax [task name] [delimiter] [time specification]
     * @param taskType         Either TaskType.EVENT or TaskType.DEADLINE
     * @return Returns a String[] object containing the task name in the first index [0] and
     *         the time specification in the second index [1].
     * @throws DukeException If taskType is NOT TaskType.EVENT or TaskType.DEADLINE, 
     *                       or if task name, delimiter or time specification is missing
     */
    protected String[] getTaskDetailTokens(String taskDetailString, TaskList.TaskType taskType, Display ui)
            throws DukeException {
        taskDetailString = taskDetailString.trim();
        // Exception is thrown here if taskType does not belong to TaskType.EVENT or TaskType.DEADLINE
        if (isDelimiterWithoutTaskName(taskDetailString, taskType, ui)) {
            throw new DukeException(Display.getErrorMessage(Display.ErrorType.EMPTY_TASK_NAME));
        }

        String[] taskDetailTokens;
        String regexPattern = TaskList.getRegexPatternOfTask(taskType);
        taskDetailTokens = taskDetailString.split(regexPattern, 2);
        boolean isTaskNameOnly = taskDetailTokens.length < 2;

        if (isTaskNameOnly) {
            if (taskType == TaskList.TaskType.EVENT) {
                throw new DukeException(Display.getErrorMessage(Display.ErrorType.MISSING_EVENT_DELIMITER));
            } else {
                throw new DukeException(Display.getErrorMessage(Display.ErrorType.MISSING_DEADLINE_DELIMITER));
            }
        }
        return taskDetailTokens;
    }

    /**
     * Standard constructor, creates a CreateTaskCommand object with a specified argument
     * 
     * @param commandArgs arguments that contain the task name and time specification (if any) of the task
     */
    public CreateTaskCommand(String commandArgs) {
        super (commandArgs);
    }
}
