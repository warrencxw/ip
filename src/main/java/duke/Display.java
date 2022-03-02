package duke;

import java.util.Scanner;

/**
 * Represents the interface between the user and the program.
 * Handles the printing of any messages from the program to the user
 * and the reading of any messages from the user to the program.
 */
public class Display {
    Scanner in;

    // Types of errors
    public enum ErrorType {
        MISSING_TASK_NO, INVALID_TASK_NO, EMPTY_INPUT, EMPTY_TASK_NAME,
        MISSING_EVENT_DELIMITER, MISSING_DEADLINE_DELIMITER, INVALID_DATE,
        MISSING_SUBSTRING, EMPTY_TASK_LIST, COMMAND_NOT_RECOGNISED, CSV_DELIMITER_IN_TASK,
        FILE_CREATION_FAILED, SAVE_LOAD_FAILED, SAVE_WRITE_FAILED, MALFORMED_CSV_RECORD
    }

    // Error Messages
    private static final String ERROR_MISSING_TASK_NO =
            "Please specify a task number.";
    private static final String ERROR_INVALID_TASK_NO =
            "The input task number you have entered is invalid.";
    private static final String ERROR_EMPTY_INPUT =
            "Please enter a command below. Enter 'help' to see available commands.";
    private static final String ERROR_EMPTY_TASK_NAME =
            "Please enter a name for your task.\n"
                    + "SYNTAX: todo <todo name>\n"
                    + "        event <event name> /at <YYYY-MM-DD>\n"
                    + "        deadline <deadline name> /by <YYYY-MM-DD>";
    private static final String ERROR_MISSING_EVENT_DELIMITER =
            "Please specify a time range for the event with the delimiter \"/at\"\n"
                    + "SYNTAX: event <event name> /at <YYYY-MM-DD>";
    private static final String ERROR_MISSING_DEADLINE_DELIMITER =
            "Please specify a deadline for the task with the delimiter \"/by\"\n"
                    + "SYNTAX: deadline <deadline name> /by <YYYY-MM-DD>";
    private static final String ERROR_MISSING_SUBSTRING =
            "Please specify a substring to search the task list with.\n" 
                    + "SYNTAX: find <substring>";
    private static final String ERROR_INVALID_DATE =
            "Please specify the date in a valid format as follows.\n"
                    + "SYNTAX: event <event name> /at <YYYY-MM-DD>\n"
                    + "        deadline <deadline name> /by <YYYY-MM-DD>";
    private static final String ERROR_EMPTY_TASK_LIST =
            "The task list is empty!";
    private static final String ERROR_COMMAND_NOT_RECOGNISED =
            "The input that you have entered is not recognised, enter 'help' to see available commands.";
    private static final String ERROR_CSV_DELIMITER_IN_TASK =
            "The delimiter '" + Duke.CSV_DELIMITER + "' is not allowed to be included in any part of the Task.\n"
                    + "Please try again after omitting it!";
    private static final String ERROR_FILE_CREATION_FAILED =
            "The save system was unable to create the save file for this program. Any tasks created will not be saved.";
    private static final String ERROR_SAVE_LOAD_FAILED =
            "The save system attempted to look for a save file, but encountered errors in the process of doing so.\n"
                    + "The program will proceed with running as if there was no save files.";
    private static final String ERROR_SAVE_WRITE_FAILED =
            "The save system attempted to write to a save file, but encountered errors in the process of doing so.\n"
                    + "The program will proceed with running without saving.";
    private static final String ERROR_MALFORMED_CSV_RECORD =
            "The save file contains one or more malformed CSV records.\n"
                    + "As the malformed CSV records have not been loaded, please check using the 'list' command for\n"
                    + "the entries that may have been malformed. Please quit immediately and rectify any errors\n"
                    + "in the file to prevent the save file from being overwritten.";

    // Text Constants
    public static final String RABBIT_ASCII =
            "                      /|      __\n"
                    + "*             +      / |   ,-~ /             +\n"
                    + "     .              Y :|  //  /                .         *\n"
                    + "         .          | jj /( .^     *\n"
                    + "               *    >-\"~\"-v\"              .        *        .\n"
                    + "*                  /       Y\n"
                    + "   .     .        jo  o    |     .            +\n"
                    + "                 ( ~T~     j                     +     .\n"
                    + "      +           >._-' _./         +\n"
                    + "               /| ;-\"~ _  l\n"
                    + "  .           / l/ ,-\"~    \\     +\n"
                    + "              \\//\\/      .- \\\n"
                    + "       +       Y        /    Y\n"
                    + "               l       I     !\n"
                    + "               ]\\      _\\    /\"\\\n"
                    + "              (\" ~----( ~   Y.  )\n"
                    + "          ~~~~~~~~~~~~~~~~~~~~~~~~~~\n";
    public static final String WARREN_ASCII =
            ",--.   ,--.\n"
                    + "|  |   |  | ,--,--.,--.--.,--.--. ,---. ,--,--,\n"
                    + "|  |.'.|  |' ,-.  ||  .--'|  .--'| .-. :|      \\\n"
                    + "|   ,'.   |\\ '-'  ||  |   |  |   \\   --.|  ||  |\n"
                    + "'--'   '--' `--`--'`--'   `--'    `----'`--''--'\n";
    public static final String GREETING_MESSAGE =
            "Hello,\n" + WARREN_ASCII
                    + "What can I do for you? (enter 'help' for available commands!)";
    public static final String FAREWELL_MESSAGE = "Bye. Hope to see you again soon!";
    public static final String HELP_MESSAGE =
            "| AVAILABLE COMMANDS |\n"
                    + "  1. list\n"
                    + "        Displays the full list of tasks that have been saved.\n"
                    + "  2. todo <task name>\n"
                    + "        Creates a new todo list entry.\n"
                    + "  3. event <event name> /at <YYYY-MM-DD>\n"
                    + "        Creates a new event entry with its accompanying time of occurrence.\n"
                    + "  4. deadline <deadline name> /by <YYYY-MM-DD>\n"
                    + "        Creates a new deadline entry with its accompanying due date.\n"
                    + "  5. mark <task entry number>\n"
                    + "        Marks the entry as done, see list for entry numbers.\n"
                    + "  6. unmark <task entry number>\n"
                    + "        Marks the entry as incomplete, see list for entry numbers.\n"
                    + "  7. find <substring>\n"
                    + "        Lists tasks that contain <substring> in their name.\n"
                    + "  8. help / ?\n"
                    + "        Displays this help menu.\n"
                    + "  9. delete <task entry number>\n"
                    + "        Deletes a task entry, see list for entry numbers.\n"
                    + "  10. clear\n"
                    + "        Clears all tasks that have been saved thus far after a confirmation message.\n"
                    + "  11. bye / exit / quit\n"
                    + "        Displays the farewell message and closes the application.";

    /**
     * Simply prints out a dividing line to standard output
     */
    public void printDivider() {
        System.out.println("____________________________________________________________");
    }

    /**
     * Prints out the greeting message to standard output
     */
    public void printGreetingMessage() {
        printDivider();
        System.out.println(RABBIT_ASCII);
        printDivider();
        System.out.println(GREETING_MESSAGE);
        printDivider();
    }

    /**
     * Prints out the farewell message to standard output
     */
    public void printFarewellMessage() {
        System.out.println(FAREWELL_MESSAGE);
    }

    /**
     * Prints out the help message to standard output
     */
    public void printHelpMessage() {
        System.out.println(HELP_MESSAGE);
    }

    /**
     * Prints out error message based on what type of error has been encountered by the program
     *
     * @param error The error type encountered by the program
     */
    public void printError(ErrorType error) {
        switch (error) {
        case MISSING_TASK_NO:
            System.out.println(ERROR_MISSING_TASK_NO);
            break;
        case INVALID_TASK_NO:
            System.out.println(ERROR_INVALID_TASK_NO);
            break;
        case EMPTY_INPUT:
            System.out.println(ERROR_EMPTY_INPUT);
            break;
        case EMPTY_TASK_NAME:
            System.out.println(ERROR_EMPTY_TASK_NAME);
            break;
        case MISSING_EVENT_DELIMITER:
            System.out.println(ERROR_MISSING_EVENT_DELIMITER);
            break;
        case MISSING_DEADLINE_DELIMITER:
            System.out.println(ERROR_MISSING_DEADLINE_DELIMITER);
            break;
        case MISSING_SUBSTRING:
            System.out.println(ERROR_MISSING_SUBSTRING);
            break;
        case INVALID_DATE:
            System.out.println(ERROR_INVALID_DATE);
            break;
        case EMPTY_TASK_LIST:
            System.out.println(ERROR_EMPTY_TASK_LIST);
            break;
        case COMMAND_NOT_RECOGNISED:
            System.out.println(ERROR_COMMAND_NOT_RECOGNISED);
            break;
        case CSV_DELIMITER_IN_TASK:
            System.out.println(ERROR_CSV_DELIMITER_IN_TASK);
            break;
        case FILE_CREATION_FAILED:
            System.out.println(ERROR_FILE_CREATION_FAILED);
            break;
        case SAVE_LOAD_FAILED:
            System.out.println(ERROR_SAVE_LOAD_FAILED);
            break;
        case SAVE_WRITE_FAILED:
            System.out.println(ERROR_SAVE_WRITE_FAILED);
            break;
        case MALFORMED_CSV_RECORD:
            System.out.println(ERROR_MALFORMED_CSV_RECORD);
            break;
        }
    }

    /**
     * Returns the error message corresponding to the error type that may be experienced by the program
     *
     * @param error The error type encountered by the program
     * @return a String object containing the error message for the specified error type
     */
    public static String getErrorMessage(ErrorType error) {
        switch (error) {
        case MISSING_TASK_NO:
            return ERROR_MISSING_TASK_NO;
        case INVALID_TASK_NO:
            return ERROR_INVALID_TASK_NO;
        case EMPTY_INPUT:
            return ERROR_EMPTY_INPUT;
        case EMPTY_TASK_NAME:
            return ERROR_EMPTY_TASK_NAME;
        case MISSING_EVENT_DELIMITER:
            return ERROR_MISSING_EVENT_DELIMITER;
        case MISSING_DEADLINE_DELIMITER:
            return ERROR_MISSING_DEADLINE_DELIMITER;
        case MISSING_SUBSTRING:
            return ERROR_MISSING_SUBSTRING;
        case INVALID_DATE:
            return ERROR_INVALID_DATE;
        case EMPTY_TASK_LIST:
            return ERROR_EMPTY_TASK_LIST;
        case COMMAND_NOT_RECOGNISED:
            return ERROR_COMMAND_NOT_RECOGNISED;
        case CSV_DELIMITER_IN_TASK:
            return ERROR_CSV_DELIMITER_IN_TASK;
        case FILE_CREATION_FAILED:
            return ERROR_FILE_CREATION_FAILED;
        case SAVE_LOAD_FAILED:
            return ERROR_SAVE_LOAD_FAILED;
        case SAVE_WRITE_FAILED:
            return ERROR_SAVE_WRITE_FAILED;
        case MALFORMED_CSV_RECORD:
            return ERROR_MALFORMED_CSV_RECORD;
        }
        return "";
    }

    /**
     * Prints out a message specified to standard output without line breaks.
     *
     * @param message String object containing the message to be printed
     */
    public void printMessage(String message) {
        System.out.print(message);
    }

    /**
     * Prints out the String representation of an Object to standard output without line breaks.
     *
     * @param message An Object of which its String representation is to be printed out
     */
    public void printMessage(Object message) {
        printMessage(message.toString());
    }

    /**
     * Prints out a message specified to standard output with line breaks.
     *
     * @param message String object containing the message to be printed
     */
    public void printlnMessage(String message) {
        System.out.println(message);
    }

    /**
     * Prints out the String representation of an Object to standard output with line breaks.
     *
     * @param message An Object of which its String representation is to be printed out
     */
    public void printlnMessage(Object message) {
        printlnMessage(message.toString());
    }

    /**
     * Reads a line of input from the user from standard input (or any other input streams depending
     * on the <code>Scanner in</code> object initialisation in the constructor) into a String object.
     *
     * @return a String object containing a single line of input from standard input
     */
    public String getNextLine() {
        return in.nextLine().trim();
    }

    /**
     * Prints out a prepending substring before reading a line of input from the user from standard input
     * (or any other input streams depending on the <code>Scanner in</code> object initialisation in the constructor)
     * into a String object.
     *
     * @param prepend a substring to be printed out before the user's input
     * @return a String object containing a single line of input from standard input
     */
    public String getNextLineWithPrepend(String prepend) {
        printMessage(prepend);
        return getNextLine();
    }

    /**
     * Creates a Display object to handle all message printing to standard output and data reading from the
     * specified Scanner object in the parameter.
     *
     * @param in a Scanner object from which all messages in the application will read from
     */
    public Display(Scanner in) {
        this.in = in;
    }

    /**
     * Creates a Display object to handle all message printing to standard output and data reading from
     * standard input.
     */
    public Display() {
        in = new Scanner(System.in);
    }
}