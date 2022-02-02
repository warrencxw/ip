import java.util.Scanner;

public class Duke {
    // Member attributes
    private static int inputCount = 0;
    private static final int MAX_COUNT = 100;
    private static final Task[] tasks = new Task[MAX_COUNT];

    // Error Messages
    public static final String ERROR_MISSING_TASK_NO =
            "Please specify a task number to mark.";
    public static final String ERROR_EMPTY_INPUT =
            "Please enter either a task to do, mark/unmark <task number> or list";
    public static final String ERROR_MISSING_EVENT_DELIMITER =
            "Please specify a time range for the event with the delimiter \"/at\"\n" +
                    "SYNTAX: event <event name> /at <time range>";
    public static final String ERROR_MISSING_DEADLINE_DELIMITER =
            "Please specify a deadline for the task with the delimiter \"/by\"\n" +
                    "SYNTAX: deadline <deadline name> /by <due date>";
    public static final String ERROR_INVALID_TASK_NUMBER =
            "The input task number you have entered is invalid.";
    public static final String ERROR_EMPTY_LIST =
            "The task list is empty!";
    public static final String ERROR_INPUT_LIMIT_REACHED =
            "We have reached the limit of " + MAX_COUNT + " items that can be added to the list.\n";

    // REGEX PATTERNS
    public static final String REGEX_PATTERN_AT = "\\s/at\\s";
    public static final String REGEX_PATTERN_BY = "\\s/by\\s";
    public static final String REGEX_PATTERN_WHITESPACES = "\\s";

    // Misc Constants
    public static final String INPUT_PREPEND = " > ";
    public static final String HELP_MESSAGE =
            "| AVAILABLE COMMANDS |\n" +
                    "  1. list\n" +
                    "        Displays the full list of tasks that have been saved.\n" +
                    "  2. todo <task name>\n" +
                    "        Creates a new todo list entry.\n" +
                    "  3. event <event name> /at <time range>\n" +
                    "        Creates a new event entry with its accompanying time of occurrence.\n" +
                    "  4. deadline <deadline name> /by <due date>\n" +
                    "        Creates a new deadline entry with its accompanying due date.\n" +
                    "  5. mark <task entry number>\n" +
                    "        Marks the entry as done, see list for entry numbers.\n" +
                    "  6. unmark <task entry number>\n" +
                    "        Marks the entry as incomplete, see list for entry numbers.\n" +
                    "  7. help / ?\n" +
                    "        Displays this help menu.\n" +
                    "  8. bye\n" +
                    "        Displays the farewell message and closes the application.";
    public static final String RABBIT_ASCII =
            "                      /|      __\n" +
                    "*             +      / |   ,-~ /             +\n" +
                    "     .              Y :|  //  /                .         *\n" +
                    "         .          | jj /( .^     *\n" +
                    "               *    >-\"~\"-v\"              .        *        .\n" +
                    "*                  /       Y\n" +
                    "   .     .        jo  o    |     .            +\n" +
                    "                 ( ~T~     j                     +     .\n" +
                    "      +           >._-' _./         +\n" +
                    "               /| ;-\"~ _  l\n" +
                    "  .           / l/ ,-\"~    \\     +\n" +
                    "              \\//\\/      .- \\\n" +
                    "       +       Y        /    Y\n" +
                    "               l       I     !\n" +
                    "               ]\\      _\\    /\"\\\n" +
                    "              (\" ~----( ~   Y.  )\n" +
                    "          ~~~~~~~~~~~~~~~~~~~~~~~~~~\n";
    public static final String WARREN_ASCII =
            ",--.   ,--.\n" +
                    "|  |   |  | ,--,--.,--.--.,--.--. ,---. ,--,--,\n" +
                    "|  |.'.|  |' ,-.  ||  .--'|  .--'| .-. :|      \\\n" +
                    "|   ,'.   |\\ '-'  ||  |   |  |   \\   --.|  ||  |\n" +
                    "'--'   '--' `--`--'`--'   `--'    `----'`--''--'\n";
    public static final String GREETING_MESSAGE =
            "Hello,\n" + WARREN_ASCII +
                    "What can I do for you? (enter 'hello' for available commands!)";
    public static final String FAREWELL_MESSAGE = "Bye. Hope to see you again soon!";

    /**
     * Simply prints out a dividing line to standard output
     */
    public static void printDivider() {
        System.out.println("____________________________________________________________");
    }

    /**
     * Prints out the greeting message to standard output
     */
    public static void printGreet() {
        printDivider();
        System.out.println(RABBIT_ASCII);
        printDivider();
        System.out.println(GREETING_MESSAGE);
        printDivider();
    }

    /**
     * Prints out the farewell message to standard output
     */
    public static void printFarewell() {
        System.out.println(FAREWELL_MESSAGE);
        printDivider();
    }

    /**
     * Takes the provided input string and saves it into the array of Task "tasks"
     * If the number of tasks exceed MAX_COUNT, the input string will not be saved.
     *
     * @param input    Input string to be saved
     * @param taskType Type of task to save as
     */
    public static void saveInput(String input, Task.TaskType taskType) {
        if (inputCount >= MAX_COUNT) {
            System.out.println(ERROR_INPUT_LIMIT_REACHED +
                    "The last entry, \"" + input + "\", will not be added to the list.");
        } else {
            String[] inputs;
            boolean isTaskNameOnly;
            switch (taskType) {
            case TODO:
                tasks[inputCount] = new Todo(input);
                break;
            case EVENT:
                inputs = input.split(REGEX_PATTERN_AT, 2);
                isTaskNameOnly = inputs.length < 2;
                if (isTaskNameOnly) {
                    System.out.println(ERROR_MISSING_EVENT_DELIMITER);
                    return;
                } else {
                    tasks[inputCount] = new Event(inputs[0], inputs[1]);
                }
                break;
            case DEADLINE:
                inputs = input.split(REGEX_PATTERN_BY, 2);
                isTaskNameOnly = inputs.length < 2;
                if (isTaskNameOnly) {
                    System.out.println(ERROR_MISSING_DEADLINE_DELIMITER);
                    return;
                } else {
                    tasks[inputCount] = new Deadline(inputs[0], inputs[1]);
                }
                break;
            default:
                tasks[inputCount] = new Task(input);
                break;
            }
            tasks[inputCount].printAddedMessage();
            inputCount++;
        }
    }

    /**
     * Prints out the full list of inputs given by the user
     */
    public static void printTaskList() {
        System.out.println("| Task List |");
        if (inputCount == 0) {
            System.out.println(ERROR_EMPTY_LIST);
        } else {
            for (int i = 0; i < inputCount; i++) {
                System.out.println((i + 1) + ". " + tasks[i].toString());
            }
        }
    }

    /**
     * Modifies the task identified by the input task number and print out a confirmation message.
     *
     * @param shouldMarkTask Determines whether to set task to done or not done
     * @param taskNoString   The input task number that identifies the task in "tasks"
     */
    public static void markTask(boolean shouldMarkTask, String taskNoString) {
        // Index in Array
        int taskNo;
        try {
            taskNo = Integer.parseInt(taskNoString) - 1;
        } catch (NumberFormatException exception) {
            System.out.println(ERROR_INVALID_TASK_NUMBER);
            System.out.println("Please enter a value between 1 and " + inputCount);
            return;
        }

        if (taskNo < 0 || taskNo >= inputCount) {
            System.out.println(ERROR_INVALID_TASK_NUMBER);
            if (inputCount == 0) {
                System.out.println(ERROR_EMPTY_LIST);
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
     * Reads in input from standard input and processes the input to determine
     * what operations to carry out by calling other helper methods.
     */
    public static void processInput() {
        Scanner in = new Scanner(System.in);
        System.out.print(INPUT_PREPEND);
        String input = in.nextLine();
        String[] inputs = input.split(REGEX_PATTERN_WHITESPACES, 2);

        boolean isCommandOnly;
        while (inputs.length > 0) {
            printDivider();
            switch (inputs[0]) {
            case "": // EMPTY INPUT
                System.out.println(ERROR_EMPTY_INPUT);
                break;
            case "?": // DISPLAY HELP MENU, FALLTHROUGH, IDENTICAL COMMAND
            case "help":
                System.out.println(HELP_MESSAGE);
                break;
            case "list": // LIST OUT TASK LIST
                printTaskList();
                break;
            case "mark": // MARK TASK AS DONE
                isCommandOnly = inputs.length < 2;
                if (isCommandOnly) {
                    System.out.println(ERROR_MISSING_TASK_NO);
                } else {
                    markTask(true, inputs[1]);
                }
                break;
            case "unmark": // MARK TASK AS NOT DONE
                isCommandOnly = inputs.length < 2;
                if (isCommandOnly) {
                    System.out.println(ERROR_MISSING_TASK_NO);
                } else {
                    markTask(false, inputs[1]);
                }
                break;
            case "todo": // CREATE NEW TODO
                saveInput(inputs[1], Task.TaskType.TODO);
                break;
            case "deadline": // CREATE NEW DEADLINE
                saveInput(inputs[1], Task.TaskType.DEADLINE);
                break;
            case "event": // CREATE NEW EVENT
                saveInput(inputs[1], Task.TaskType.EVENT);
                break;
            case "bye":
                printFarewell();
                return;
            default:
                saveInput(input, Task.TaskType.DEFAULT);
                break;
            }
            printDivider();
            System.out.print(INPUT_PREPEND);

            input = in.nextLine();
            inputs = input.split(REGEX_PATTERN_WHITESPACES, 2);
        }
    }

    public static void main(String[] args) {
        printGreet();
        processInput();
    }
}
