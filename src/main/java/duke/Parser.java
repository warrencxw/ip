package duke;

import duke.command.Command;
import duke.command.ClearCommand;
import duke.command.DeadlineCommand;
import duke.command.DeleteCommand;
import duke.command.EmptyCommand;
import duke.command.EventCommand;
import duke.command.ExitCommand;
import duke.command.FindCommand;
import duke.command.HelpCommand;
import duke.command.ListCommand;
import duke.command.MarkTaskCommand;
import duke.command.TodoCommand;
import duke.command.UnrecognisedCommand;
import duke.task.TaskList;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/**
 * A static class containing methods to process and make sense of user input.
 * Connects user inputs to the right subclass of the Command class to be run.
 */=
public class Parser {
    // Regex patterns
    public static final String REGEX_PATTERN_WHITESPACES = "\\s";

    /**
     * Processes the input to be split into a String array of { commandString, commandArgs }, separated by spaces.
     * commandArgs is set to "" if not provided, where input is only a single token and not separated by spaces.
     *
     * @param input input string from the command line
     * @return String array of size 2 containing { commandString, commandArgs }
     */
    private static String[] getArguments(String input) {
        String[] inputs = input.split(REGEX_PATTERN_WHITESPACES, 2);
        String[] output = new String[]{inputs[0], ""};
        if (inputs.length < 2) {
            return output;
        }
        output[1] = inputs[1];
        return output;
    }
  
    // TODO
    public static LocalDate parseDateFromString(String input) throws DukeException {
        LocalDate date;
        try {
            date = LocalDate.parse(input);
        } catch (DateTimeParseException exception) {
            throw new DukeException (Display.getErrorMessage(Display.ErrorType.INVALID_DATE));
        }
        return date;
    }
    
   /**
     * Reads in a String object representing the task number and parses the String object to get the numeric index.
     * If taskNoString is a valid integer, it is further checked if it is within the array index limits of the
     * TaskList object. Returns an integer representing the index of the task.
     *
     * @param input The input task number string that identifies the task in "tasks"
     * @param ui    A Display object to manage printing of errors and other messages
     * @param tasks The TaskList object of which the array index limits are checked against for the task number
     * @return Returns an integer in [0, (tasks.getSize()-1)]
     * @throws DukeException If taskNoString is not a valid integer, tasks is empty or
     *                       taskNoString is not within [1, (tasks.getSize())]
     */
    public static int parseTaskNoFromString(String input, Display ui, TaskList tasks) throws DukeException {
        if (input.trim().isEmpty()) {
            throw new DukeException(Display.getErrorMessage(Display.ErrorType.MISSING_TASK_NO));
        }

        int taskNo;
        try {
            // -1 to get task index for 0-indexing
            taskNo = Integer.parseInt(input) - 1;
        } catch (NumberFormatException exception) {
            ui.printError(Display.ErrorType.INVALID_TASK_NO);
            throw new DukeException("Please enter a value between 1 and " + tasks.getSize());
        }

        // Check if index is within array limits
        boolean isTaskNoOutOfRange = taskNo < 0 || taskNo >= tasks.getSize();
        if (isTaskNoOutOfRange) {
            ui.printError(Display.ErrorType.INVALID_TASK_NO);
            if (tasks.getSize() == 0) {
                throw new DukeException(Display.getErrorMessage(Display.ErrorType.EMPTY_TASK_LIST));
            } else {
                throw new DukeException("Please enter a value between 1 and " + tasks.getSize());
            }
        }

        return taskNo;
    }

    /**
     * Parses the input string and returns a Command object with which the correct command can be run
     *
     * @param input raw unprocessed line of input from the user
     * @return an appropriate Command object to carry out instructions of the user
     */
    public static Command getCommand(String input) {
        // inputs: Array of Strings { commandString, commandArguments }
        String[] inputs = getArguments(input.trim());
        String commandString = inputs[0];
        String commandArgs = inputs[1];

        switch (commandString.toLowerCase()) {
        // EMPTY INPUT
        case "":
            return new EmptyCommand();
        // DISPLAY HELP MENU, FALLTHROUGH : IDENTICAL COMMAND
        case "?":
        case "help":
            return new HelpCommand();
        // LIST OUT TASK LIST
        case "list":
            return new ListCommand();
        // MARK TASK AS DONE
        case "mark":
            return new MarkTaskCommand(commandArgs, true);
        // MARK TASK AS NOT DONE
        case "unmark":
            return new MarkTaskCommand(commandArgs, false);
        // CREATE NEW TODO
        case "todo":
            return new TodoCommand(commandArgs);
        // CREATE NEW DEADLINE
        case "deadline":
            return new DeadlineCommand(commandArgs);
        // CREATE NEW EVENT
        case "event":
            return new EventCommand(commandArgs);
        case "find":
            return new FindCommand(commandArgs);
        // DELETE TASK, FALLTHROUGH : IDENTICAL COMMANDS
        case "delete":
        case "remove":
            return new DeleteCommand(commandArgs);
        case "clear":
            return new ClearCommand();
        // CONCLUDE SESSION, FALLTHROUGH : IDENTICAL COMMANDS
        case "exit":
        case "quit":
        case "bye":
            return new ExitCommand();
        // UNRECOGNISED INPUT
        default:
            return new UnrecognisedCommand();
        }
    }
}
