public class TaskList {
    // Member attributes
    private static int inputCount = 0;
    public static final int MAX_COUNT = 100;
    private static final Task[] tasks = new Task[MAX_COUNT];

    // Regex patterns
    public static final String REGEX_PATTERN_AT = "\\s/at\\s";
    public static final String REGEX_PATTERN_BY = "\\s/by\\s";

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
        String taskDetailString = inputs[1];

        if (inputCount >= MAX_COUNT) {
            Display.printError(Display.ErrorType.INPUT_LIMIT_REACHED);
            System.out.println("The last entry, \"" + taskDetailString + "\", will not be added to the list.");
            return;
        }

        String[] taskDetailTokens;
        boolean isTaskNameOnly;
        switch (taskType) {
        case TODO:
            tasks[inputCount] = new Todo(taskDetailString);
            break;
        case EVENT:
            taskDetailTokens = taskDetailString.split(REGEX_PATTERN_AT, 2);
            isTaskNameOnly = taskDetailTokens.length < 2;
            if (isTaskNameOnly) {
                Display.printError(Display.ErrorType.MISSING_EVENT_DELIMITER);
                return;
            } else {
                tasks[inputCount] = new Event(taskDetailTokens[0], taskDetailTokens[1]);
            }
            break;
        case DEADLINE:
            taskDetailTokens = taskDetailString.split(REGEX_PATTERN_BY, 2);
            isTaskNameOnly = taskDetailTokens.length < 2;
            if (isTaskNameOnly) {
                Display.printError(Display.ErrorType.MISSING_DEADLINE_DELIMITER);
                return;
            } else {
                tasks[inputCount] = new Deadline(taskDetailTokens[0], taskDetailTokens[1]);
            }
            break;
        default:
            tasks[inputCount] = new Task(taskDetailString);
            break;
        }
        tasks[inputCount].printAddedMessage();
        inputCount++;
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
    public static void markTask(boolean shouldMarkTask, String taskNoString) {
        // Index in Array
        int taskNo;
        try {
            taskNo = Integer.parseInt(taskNoString) - 1;
        } catch (NumberFormatException exception) {
            Display.printError(Display.ErrorType.INVALID_TASK_NO);
            System.out.println("Please enter a value between 1 and " + inputCount);
            return;
        }

        boolean isTaskNoOutOfRange = taskNo < 0 || taskNo >= inputCount;
        if (isTaskNoOutOfRange) {
            Display.printError(Display.ErrorType.INVALID_TASK_NO);
            if (inputCount == 0) {
                Display.printError(Display.ErrorType.EMPTY_TASK_LIST);
            } else {
                System.out.println("Please enter a value between 1 and " + inputCount);
            }
        } else {
            // Check if already marked / unmarked
            if (tasks[taskNo].isDone() == shouldMarkTask) {
                System.out.println("Your task has already been marked as " + (shouldMarkTask ? "done!" : "not done!"));
            } else {
                tasks[taskNo].setDone(shouldMarkTask);
                System.out.println("Your task is now marked as " + (shouldMarkTask ? "done!" : "not done!"));
            }
            System.out.println("> " + (taskNo + 1) + ". " + tasks[taskNo].toString());
        }
    }

    /**
     * Checks if there are arguments in the 'mark'/'unmark' command then runs markTask() to modify completion of
     * the specified task if a valid numerical argument is indicated. Rejects and prints an error message otherwise,
     * depending on the type of input provided.
     *
     * @param shouldMarkTask Determines whether to set task to done or not done
     * @param inputs         List of input tokens provided by the user
     */
    static void processInputAndMarkTask(boolean shouldMarkTask, String[] inputs) {
        boolean isCommandOnly = inputs.length < 2;
        if (isCommandOnly) {
            Display.printError(Display.ErrorType.MISSING_TASK_NO);
        } else {
            markTask(shouldMarkTask, inputs[1]);
        }
    }
}