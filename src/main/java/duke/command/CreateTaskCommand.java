package duke.command;

import duke.Display;
import duke.DukeException;
import duke.task.TaskList;

public abstract class CreateTaskCommand extends Command {
    /**
     * Takes in an input string and returns whether the input string starts with a time specification delimiter.
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
     * Takes in an input string and returns a String array of {task name, time specification} if input syntax is valid.
     * Returns NULL if any component is missing, i.e. invalid syntax.
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
    
    public CreateTaskCommand(String commandArgs) {
        super (commandArgs);
    }
}
