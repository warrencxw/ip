package duke.task;

import duke.Display;
import duke.DukeException;

public class TaskList {
    // Member attributes
    private static int inputCount = 0;
    public static final int MAX_COUNT = 100;
    private static final Task[] tasks = new Task[MAX_COUNT];

    // Regex patterns
    public static final String REGEX_PATTERN_AT = "\\s/at\\s";
    public static final String REGEX_PATTERN_BY = "\\s/by\\s";

    // String patterns
    public static final String STRING_PATTERN_AT = "/at";
    public static final String STRING_PATTERN_BY = "/by";

    // Exception messages
    public static final String EXCEPTION_MESSAGE_REGEX_PATTERN_TODO_REQUESTED =
            "TaskType TODO does not have a Regex pattern";
    public static final String EXCEPTION_MESSAGE_INVALID_TASKTYPE =
            "Invalid TaskType argument provided to method.";
    public static final String EXCEPTION_MESSAGE_STRING_PATTERN_TODO_REQUESTED =
            "TaskType TODO does not have a String pattern.";

    // Subclasses
    public enum TaskType {
        TODO, DEADLINE, EVENT
    }

    /**
     * Takes the provided input string and saves it into the array of Task "tasks"
     * If the number of tasks exceed MAX_COUNT, the input string will not be saved.
     *
     * @param inputs   Input string to be saved
     * @param taskType Type of task to save as
     */
    public static void saveInputAsTask(String[] inputs, TaskType taskType) {
        if (inputs.length <= 1) {
            Display.printError(Display.ErrorType.EMPTY_TASK_NAME);
            return;
        }
        String taskDetailString = inputs[1].trim();

        if (inputCount >= MAX_COUNT) {
            Display.printError(Display.ErrorType.INPUT_LIMIT_REACHED);
            System.out.println("The last entry, \"" + taskDetailString + "\", will not be added to the list.");
            return;
        }

        switch (taskType) {
        case TODO:
            tasks[inputCount] = new Todo(taskDetailString);
            break;
        // FALLTHROUGH: TASKTYPE DIFFERENTIATION IN METHOD.
        case EVENT:
        case DEADLINE:
            tasks[inputCount] = getTaskFromInputStringWithDelimiters(taskDetailString, taskType);
            if (tasks[inputCount] == null) {
                return;
            }
            break;
        default:
            return;
        }
        tasks[inputCount].printAddedMessage();
        inputCount++;
    }

    /**
     * Returns a Task object based on the given consolidated input string and task type.
     *
     * @param taskDetailString Input string that follows the syntax [task name] [delimiter] [time specification]
     * @param taskType         Either TaskType.EVENT or TaskType.DEADLINE
     * @return Returns Event or Deadline object if taskDetailString is valid, null otherwise.
     */
    private static Task getTaskFromInputStringWithDelimiters(String taskDetailString, TaskType taskType) {
        String[] taskDetailTokens;
        try {
            taskDetailTokens = getTaskDetailTokens(taskDetailString, taskType);
        } catch (DukeException exception) {
            exception.printStackTrace();
            return null;
        }
        if (taskDetailTokens == null) {
            return null;
        }

        switch (taskType) {
        case EVENT:
            return new Event(taskDetailTokens[0].trim(), taskDetailTokens[1].trim());
        case DEADLINE:
            return new Deadline(taskDetailTokens[0].trim(), taskDetailTokens[1].trim());
        default:
            return null;
        }
    }

    /**
     * Returns String pattern of the task specified in the argument
     *
     * @param taskType Either TaskType.EVENT or TaskType.DEADLINE
     * @return String pattern of task given
     * @throws DukeException If taskType is NOT TaskType.EVENT or TaskType.DEADLINE
     */
    private static String getStringPatternOfTask(TaskType taskType) throws DukeException {
        String stringPattern;
        switch (taskType) {
        case TODO:
            throw new DukeException(EXCEPTION_MESSAGE_STRING_PATTERN_TODO_REQUESTED);
        case EVENT:
            stringPattern = STRING_PATTERN_AT;
            break;
        case DEADLINE:
            stringPattern = STRING_PATTERN_BY;
            break;
        default:
            throw new DukeException(EXCEPTION_MESSAGE_INVALID_TASKTYPE);
        }
        return stringPattern;
    }

    /**
     * Returns Regex pattern of the task specified in the argument
     *
     * @param taskType Either TaskType.EVENT or TaskType.DEADLINE
     * @return Regex pattern of task given
     * @throws DukeException If taskType is NOT TaskType.EVENT or TaskType.DEADLINE
     */
    private static String getRegexPatternOfTask(TaskType taskType) throws DukeException {
        String regexPattern;
        switch (taskType) {
        case TODO:
            throw new DukeException(EXCEPTION_MESSAGE_REGEX_PATTERN_TODO_REQUESTED);
        case EVENT:
            regexPattern = REGEX_PATTERN_AT;
            break;
        case DEADLINE:
            regexPattern = REGEX_PATTERN_BY;
            break;
        default:
            throw new DukeException(EXCEPTION_MESSAGE_INVALID_TASKTYPE);
        }
        return regexPattern;
    }

    /**
     * Takes in an input string and returns whether the input string starts with a time specification delimiter.
     *
     * @param taskDetailString Input string that follows the syntax [task name] [delimiter] [time specification]
     * @param taskType         Either TaskType.EVENT or TaskType.DEADLINE
     * @return Returns true if taskDetailString starts with the delimiter, i.e. task name is missing,
     * returns false otherwise.
     * @throws DukeException If taskType is NOT TaskType.EVENT or TaskType.DEADLINE
     */
    private static boolean isDelimiterWithoutTaskName(String taskDetailString, TaskType taskType) throws DukeException {
        String stringPattern = getStringPatternOfTask(taskType);
        boolean isMissingTaskName = taskDetailString.indexOf(stringPattern) == 0;
        if (isMissingTaskName) {
            Display.printError(Display.ErrorType.EMPTY_TASK_NAME);
            return true;
        }
        return false;
    }

    /**
     * Takes in an input string and returns a String array of {task name, time specification} if input syntax is valid.
     * Returns NULL if any component is missing, i.e. invalid syntax.
     *
     * @param taskDetailString Input string that follows the syntax [task name] [delimiter] [time specification]
     * @param taskType         Either TaskType.EVENT or TaskType.DEADLINE
     * @return Returns a String[] object containing the task name in the first index [0] and
     * the time specification in the second index [1].
     * Returns null and prints an error message if task name, delimiter or time specification
     * is missing
     * @throws DukeException If taskType is NOT TaskType.EVENT or TaskType.DEADLINE
     */
    private static String[] getTaskDetailTokens(String taskDetailString, TaskType taskType) throws DukeException {
        taskDetailString = taskDetailString.trim();
        // Exception is thrown here if taskType does not belong to TaskType.EVENT or TaskType.DEADLINE
        if (isDelimiterWithoutTaskName(taskDetailString, taskType)) {
            return null;
        }

        String[] taskDetailTokens;
        String regexPattern = getRegexPatternOfTask(taskType);
        taskDetailTokens = taskDetailString.split(regexPattern, 2);
        boolean isTaskNameOnly = taskDetailTokens.length < 2;

        if (isTaskNameOnly) {
            if (taskType == TaskType.EVENT) {
                Display.printError(Display.ErrorType.MISSING_EVENT_DELIMITER);
            } else {
                Display.printError(Display.ErrorType.MISSING_DEADLINE_DELIMITER);
            }
            return null;
        }
        return taskDetailTokens;
    }

    /**
     * Prints out the full list of inputs given by the user
     */
    public static void printTaskList() {
        System.out.println("| Task List |");
        if (inputCount == 0) {
            Display.printError(Display.ErrorType.EMPTY_TASK_LIST);
        } else {
            for (int i = 0; i < inputCount; i++) {
                System.out.println((i + 1) + ". " + tasks[i].toString());
            }
        }
    }

    /**
     * Modifies the completion of the task identified by the input task number and print out a confirmation message.
     * Will not modify tasks if taskNoString is not an integer, an error message is printed instead
     *
     * @param shouldMarkTask Determines whether to set task to done or not done
     * @param taskNoString   The input task number string that identifies the task in "tasks"
     */
    private static void markTask(boolean shouldMarkTask, String taskNoString) {
        // Check if task number is numerical
        int taskNo;
        try {
            // -1 to get array index
            taskNo = Integer.parseInt(taskNoString) - 1;
        } catch (NumberFormatException exception) {
            Display.printError(Display.ErrorType.INVALID_TASK_NO);
            System.out.println("Please enter a value between 1 and " + inputCount);
            return;
        }

        // Check if index is within array limits
        boolean isTaskNoOutOfRange = taskNo < 0 || taskNo >= inputCount;
        if (isTaskNoOutOfRange) {
            Display.printError(Display.ErrorType.INVALID_TASK_NO);
            if (inputCount == 0) {
                Display.printError(Display.ErrorType.EMPTY_TASK_LIST);
            } else {
                System.out.println("Please enter a value between 1 and " + inputCount);
            }
            return;
        }

        // Check if already marked / unmarked
        if (tasks[taskNo].isDone() == shouldMarkTask) {
            System.out.println("Your task has already been marked as " + (shouldMarkTask ? "done!" : "not done!"));
        } else {
            tasks[taskNo].setDone(shouldMarkTask);
            System.out.println("Your task is now marked as " + (shouldMarkTask ? "done!" : "not done!"));
        }
        System.out.println("> " + (taskNo + 1) + ". " + tasks[taskNo].toString());
    }

    /**
     * Checks if there are arguments in the 'mark'/'unmark' command then runs markTask() to modify completion of
     * the specified task if a valid numerical argument is indicated. Rejects and prints an error message otherwise,
     * depending on the type of input provided.
     *
     * @param shouldMarkTask Determines whether to set task to done or not done
     * @param inputs         List of input tokens provided by the user
     */
    public static void processInputAndMarkTask(boolean shouldMarkTask, String[] inputs) {
        boolean isCommandOnly = inputs.length < 2;
        if (isCommandOnly) {
            Display.printError(Display.ErrorType.MISSING_TASK_NO);
        } else {
            markTask(shouldMarkTask, inputs[1]);
        }
    }
}