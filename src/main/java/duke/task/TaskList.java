package duke.task;

import duke.Display;
import duke.Duke;
import duke.DukeException;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

public class TaskList {
    // Member attributes
    private ArrayList<Task> tasks;
    private Display ui;

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
            "The save file contains one or more malformed CSV records.\n"
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
     * Returns String pattern of the task specified in the argument
     *
     * @param taskType Either TaskType.EVENT or TaskType.DEADLINE
     * @return String pattern of task given
     * @throws DukeException If taskType is NOT TaskType.EVENT or TaskType.DEADLINE
     */
    public static String getStringPatternOfTask(TaskType taskType) throws DukeException {
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
    public static String getRegexPatternOfTask(TaskType taskType) throws DukeException {
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
     * Prints out the full list of inputs given by the user
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("| Task List |");
        for (int i = 0; i < tasks.size(); i++) {
            sb.append("\n" + (i + 1) + ". " + tasks.get(i).toString());
        }
        return sb.toString();
    }

    /**
     * Reads through every single Task in the TaskList and returns an array of Strings, each String containing
     * a CSV record for each Task in the TaskList.
     *
     * @return Returns a String array of CSV records representing the entire TaskList to be saved.
     */
    public String[] getSavableCSVStrings() {
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
    public boolean isValidCSVRecord(String[] record) {
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
    public void addTaskFromCSVRecord(String[] record) throws DukeException, DateTimeParseException {
        if (!isValidCSVRecord(record)) {
            throw new DukeException(EXCEPTION_MALFORMED_CSV_RECORD);
        }
        // record = { taskType {T,E,D}, marked {Y,N}, name, dueDate/eventTime(if D/E)}

        boolean marked = record[1].equalsIgnoreCase("Y");
        LocalDate date;
        switch (record[0]) {
        case "T":
            tasks.add(new Todo(record[2], marked));
            break;
        case "E":
            date = LocalDate.parse(record[3]);
            tasks.add(new Event(record[2], marked, date));
            break;
        case "D":
            date = LocalDate.parse(record[3]);
            tasks.add(new Deadline(record[2], marked, date));
            break;
        default:
            throw new DukeException(EXCEPTION_MALFORMED_CSV_RECORD);
        }
    }

    public void clearTasks() {
        tasks.clear();
    }

    public void addTask(Task task) {
        if (task != null) {
            tasks.add(task);
        }
    }

    public void removeTask(int taskNo) throws IndexOutOfBoundsException {
        tasks.remove(taskNo);
    }

    public Task getTask(int taskNo) throws IndexOutOfBoundsException {
        return tasks.get(taskNo);
    }

    public int getSize() {
        return tasks.size();
    }

    public TaskList() {
        tasks = new ArrayList<>();
        ui = new Display();
    }

    public TaskList(Display ui) {
        tasks = new ArrayList<>();
        this.ui = ui;
    }
}