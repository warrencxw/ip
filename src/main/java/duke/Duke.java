package duke;

import duke.task.TaskList;

import java.util.Scanner;

public class Duke {
    // Regex patterns
    public static final String REGEX_PATTERN_WHITESPACES = "\\s";
    public static final String REGEX_PATTERN_CSV_DELIMITER = "[ ]*,[ ]*";
    public static final String CSV_DELIMITER = ",";

    // Misc Constants
    public static final String INPUT_PREPEND = " > ";

    /**
     * Reads in input from standard input and processes the input to determine
     * what operations to carry out by calling other helper methods.
     * Repeats process until exit/quit/bye commands are called.
     */
    public static void processInputLoop() {
        Scanner in = new Scanner(System.in);
        System.out.print(INPUT_PREPEND);
        String input = in.nextLine().trim();
        // inputs: Array of Strings { command, furtherArguments }
        String[] inputs = input.split(REGEX_PATTERN_WHITESPACES, 2);

        boolean isCommandOnly;
        while (inputs.length > 0) {
            Display.printDivider();
            switch (inputs[0].toLowerCase()) {
            // EMPTY INPUT
            case "":
                Display.printError(Display.ErrorType.EMPTY_INPUT);
                break;
            // DISPLAY HELP MENU, FALLTHROUGH : IDENTICAL COMMAND
            case "?":
            case "help":
                Display.printHelpMessage();
                break;
            // LIST OUT TASK LIST
            case "list":
                TaskList.printTaskList();
                break;
            // MARK TASK AS DONE
            case "mark":
                TaskList.processInputAndMarkTask(true, inputs);
                SaveManager.saveChanges();
                break;
            // MARK TASK AS NOT DONE
            case "unmark":
                TaskList.processInputAndMarkTask(false, inputs);
                SaveManager.saveChanges();
                break;
            // CREATE NEW TODO
            case "todo":
                TaskList.saveInputAsTask(inputs, TaskList.TaskType.TODO);
                SaveManager.saveChanges();
                break;
            // CREATE NEW DEADLINE
            case "deadline":
                TaskList.saveInputAsTask(inputs, TaskList.TaskType.DEADLINE);
                SaveManager.saveChanges();
                break;
            // CREATE NEW EVENT
            case "event":
                TaskList.saveInputAsTask(inputs, TaskList.TaskType.EVENT);
                SaveManager.saveChanges();
                break;
            // DELETE TASK, FALLTHROUGH : IDENTICAL COMMANDS
            case "delete":
            case "remove":
                TaskList.processInputAndDeleteTask(inputs);
                break;
            // CONCLUDE SESSION, FALLTHROUGH : IDENTICAL COMMANDS
            case "exit":
            case "quit":
            case "bye":
                Display.printFarewellMessage();
                return;
            // UNRECOGNISED INPUT
            default:
                Display.printError(Display.ErrorType.COMMAND_NOT_RECOGNISED);
                break;
            }
            Display.printDivider();
            System.out.print(INPUT_PREPEND);

            input = in.nextLine().trim();
            inputs = input.split(REGEX_PATTERN_WHITESPACES, 2);
        }
    }

    public static void main(String[] args) {
        SaveManager.loadSave();
        Display.printGreetingMessage();
        processInputLoop();
    }
}
