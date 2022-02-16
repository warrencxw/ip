package duke.task;

import duke.Display;
import duke.Duke;
import duke.DukeException;
import duke.SaveManager;

import java.util.ArrayList;

public class TaskList {
    // Member attributes
    private static ArrayList<Task> tasks = new ArrayList<>();

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
    public static final String EXCEPTION_MALFORMED_CSV_RECORD =
            "The save file '" + SaveManager.SAVE_FILE_NAME + "' contains one or more malformed CSV records.\n"
                    + "The loading process will be halted. Please quit immediately and rectify any errors\n"
                    + "in the file to prevent the save file from being overwritten.";

    // Subclasses
    public enum TaskType {
        TODO, DEADLINE, EVENT
    }
    
    // Subclass Constants
    private static final int TODO_RECORD_LENGTH = 3;
    private static final int DEADLINE_EVENT_RECORD_LENGTH = 4;

    /**
     * Takes the provided input string and saves it into the array of Task "tasks"
     * Does nothing and prints out an error message if either the input is invalid,
     * or the CSV delimiter is included in any part of the task details.
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
        if (taskDetailString.contains(Duke.CSV_DELIMITER)) {
            Display.printError(Display.ErrorType.CSV_DELIMITER_IN_TASK);
            return;
        }

        switch (taskType) {
        case TODO:
            tasks.add(new Todo(taskDetailString));
            break;
        // FALLTHROUGH: TASKTYPE DIFFERENTIATION IN METHOD.
        case EVENT:
        case DEADLINE:
            Task newTask = getTaskFromInputStringWithDelimiters(taskDetailString, taskType);
            if (newTask == null) {
                return;
            }
            tasks.add(newTask);
            break;
        default:
            return;
        }
        tasks.get(tasks.size() - 1).printAddedMessage();
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
     * Returns null and prints an error message if task name, delimiter or time specification is missing
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
        if (tasks.size() == 0) {
            Display.printError(Display.ErrorType.EMPTY_TASK_LIST);
        } else {
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println((i + 1) + ". " + tasks.get(i).toString());
            }
        }
    }

    /**
     * Reads in a String object representing the task number and parses the String to get the numeric index.
     * Returns an integer representing the index of the task.
     *
     * @param taskNoString The input task number string that identifies the task in "tasks"
     * @return Returns an integer in [0, (tasks.size()-1)]
     * @throws DukeException If taskNoString is not a valid integer, tasks is empty or
     *                       taskNoString is not within [0, (tasks.size()-1)]
     */
    private static int getTaskIndexFromString(String taskNoString) throws DukeException {
        int taskNo;
        try {
            // -1 to get task index for 0-indexing
            taskNo = Integer.parseInt(taskNoString) - 1;
        } catch (NumberFormatException exception) {
            Display.printError(Display.ErrorType.INVALID_TASK_NO);
            throw new DukeException("Please enter a value between 1 and " + tasks.size());
        }

        // Check if index is within array limits
        boolean isTaskNoOutOfRange = taskNo < 0 || taskNo >= tasks.size();
        if (isTaskNoOutOfRange) {
            Display.printError(Display.ErrorType.INVALID_TASK_NO);
            if (tasks.size() == 0) {
                throw new DukeException(Display.ERROR_EMPTY_TASK_LIST);
            } else {
                throw new DukeException("Please enter a value between 1 and " + tasks.size());
            }
        }
        return taskNo;
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
            taskNo = getTaskIndexFromString(taskNoString);
        } catch (DukeException exception) {
            System.out.println(exception.getMessage());
            return;
        }

        // Check if already marked / unmarked
        if (tasks.get(taskNo).isDone() == shouldMarkTask) {
            System.out.println("Your task has already been marked as " + (shouldMarkTask ? "done!" : "not done!"));
        } else {
            tasks.get(taskNo).setDone(shouldMarkTask);
            System.out.println("Your task is now marked as " + (shouldMarkTask ? "done!" : "not done!"));
        }
        System.out.println("> " + (taskNo + 1) + ". " + tasks.get(taskNo).toString());
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

    /**
     * Checks if there are arguments in the 'delete' command and if the arguments are valid (integer and within
     * range of [1, tasks.size()]). If arguments are valid, deletes the task identified by the argument and prints
     * a success message onto standard output. Prints an error message and does not modify tasks otherwise. 
     *
     * @param inputs List of input tokens provided by the user
     */
    public static void processInputAndDeleteTask(String[] inputs) {
        boolean isCommandOnly = inputs.length < 2;
        if (isCommandOnly) {
            Display.printError(Display.ErrorType.MISSING_TASK_NO);
            return;
        }

        int taskNo;
        String taskNoString = inputs[1];
        try {
            taskNo = getTaskIndexFromString(taskNoString);
        } catch (DukeException exception) {
            System.out.println(exception.getMessage());
            return;
        }

        String successMessage =
                "I have removed the following task for you:\n"
                        + "> " + (taskNo + 1) + ". " + tasks.get(taskNo).toString() + "\n"
                        + "You now have " + (tasks.size() - 1) + " tasks in your list.";
        tasks.remove(taskNo);
        System.out.println(successMessage);
    }

    /**
     * Reads through every single Task in the TaskList and returns an array of Strings, each String containing
     * a CSV record for each Task in the TaskList.
     * 
     * @return Returns a String array of CSV records representing the entire TaskList to be saved.
     */
    public static String[] getSavableCSVStrings() {
        String[] csvStrings = new String[tasks.size()];
        for (int i = 0; i < tasks.size(); i++) {
            csvStrings[i] = tasks.get(i).getSavableCSVString();
        }
        return csvStrings;
    }

    /**
     * Reads through a single CSV record and checks if it is valid and can be loaded into the TaskList as a task.
     * 
     * @param record An array of Strings each representing a value within the CSV record
     * @return Returns true if the CSV record is valid and can be loaded as a Task, false otherwise.
     */
    public static boolean isValidCSVRecord(String[] record) {
        boolean isInvalidRecordLength = 
                record.length < TODO_RECORD_LENGTH || record.length > DEADLINE_EVENT_RECORD_LENGTH;
        if (isInvalidRecordLength) {
            return false;
        }
        boolean isValidTask =
                (record.length == TODO_RECORD_LENGTH && record[0].equalsIgnoreCase("T"))
                        || (record.length == DEADLINE_EVENT_RECORD_LENGTH 
                        && (record[0].equalsIgnoreCase("D") || record[0].equalsIgnoreCase("E")));
        boolean isValidMarking = 
                record[1].equalsIgnoreCase("Y") || record[1].equalsIgnoreCase("N");
        return isValidTask && isValidMarking;
    }

    /**
     * Reads through a single CSV record, and if valid, create a relevant Task subclass object into tasks.
     * Otherwise, throw an exception containing the error message for a malformed CSV record.
     * 
     * @param record An array of Strings each representing a value within the CSV record
     * @throws DukeException If record is a CSV record that does not follow the saving conventions as per any of
     *                       each Task subclass' getSavableCSVString() method.
     */
    public static void addTaskFromCSVRecord(String[] record) throws DukeException {
        if (!isValidCSVRecord(record)) {
            throw new DukeException(EXCEPTION_MALFORMED_CSV_RECORD); 
        }
        // record = { taskType {T,E,D}, marked {Y,N}, name, dueDate/eventTime(if D/E)}
        
        boolean marked = record[1].equalsIgnoreCase("Y");
        switch (record[0]) {
        case "T":
            tasks.add(new Todo(record[2], marked));
            break;
        case "E":
            tasks.add(new Event(record[2], marked, record[3]));
            break;
        case "D":
            tasks.add(new Deadline(record[2], marked, record[3]));
            break;
        default:
            throw new DukeException(EXCEPTION_MALFORMED_CSV_RECORD);
        }
    }
}