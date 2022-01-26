import java.util.Scanner;

public class Duke {
    // Member attributes
    private static int inputCount = 0;
    private static final int MAX_COUNT = 100;
    private static Task[] tasks = new Task[MAX_COUNT];

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
        String logo =
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
        String warrenAscii =
                ",--.   ,--.                                     \n" +
                "|  |   |  | ,--,--.,--.--.,--.--. ,---. ,--,--,  \n" +
                "|  |.'.|  |' ,-.  ||  .--'|  .--'| .-. :|      \\\n" +
                "|   ,'.   |\\ '-'  ||  |   |  |   \\   --.|  ||  |\n" +
                "'--'   '--' `--`--'`--'   `--'    `----'`--''--'\n";
        System.out.println(logo);
        printDivider();
        System.out.println("Hello,\n" + warrenAscii + "What can I do for you?");
        printDivider();
    }

    /**
     * Prints out the farewell message to standard output
     */
    public static void printFarewell() {
        printDivider();
        System.out.println("Bye. Hope to see you again soon!");
        printDivider();
    }

    /**
     * Takes the provided input string and saves it into the array of Task "tasks"
     * If the number of tasks exceed MAX_COUNT, the input string will not be saved.
     * @param input Input string to be saved
     */
    public static void saveInput(String input) {
        if (inputCount >= MAX_COUNT) {
            System.out.println("We have reached the limit of " + MAX_COUNT +
                    " items that can be added to the list.\n" +
                    "The last entry, \"" + input + "\", will not be added to the list.");
        }
        else {
            tasks[inputCount] = new Task(input);
            inputCount++;
            System.out.println("\"" + input + "\" has been added into the todo list.");
        }
    }

    /**
     *  Prints out the full list of inputs given by the user
     */
    public static void printToDoList() {
        System.out.println("| Todo List |");
        if (inputCount == 0) {
            System.out.println("The list is empty!");
        }
        else {
            for (int i = 0; i < inputCount; i++) {
                System.out.println((i + 1) + ". " + tasks[i].toPrint());
            }
        }
    }

    /**
     * Modifies the task identified by the input task number and print out a confirmation message.
     * @param shouldMarkTask Determines whether to set task to done or not done
     * @param taskNoString The input task number that identifies the task in "tasks"
     */
    public static void markTask(boolean shouldMarkTask, String taskNoString) {
        // Index in Array
        int taskNo = Integer.parseInt(taskNoString) - 1;
        if (taskNo < 0 || taskNo >= inputCount) {
            System.out.println("The input task number, \"" + (taskNo + 1) + "\" , is invalid.");
        }
        else {
            // Check if already marked / unmarked
            if (tasks[taskNo].isDone() == shouldMarkTask) {
                System.out.println("Your task has already been marked as " + (shouldMarkTask ? "done!" : "not done!"));
            }
            else {
                tasks[taskNo].setDone(shouldMarkTask);
                System.out.println("Your task is now marked as " + (shouldMarkTask ? "done!" : "not done!"));
            }
            System.out.println("> " + (taskNo + 1) + ". " + tasks[taskNo].toPrint());
        }
    }

    /**
     * Reads in input from standard input as long as the user does not
     * type in "bye", and processes the input accordingly by calling other
     * helper methods.
     */
    public static void processInput() {
        Scanner in = new Scanner(System.in);
        System.out.print(" > ");
        String input = in.nextLine();
        String[] inputs = input.split(" ");

        while (inputs.length > 0 && !inputs[0].equals("bye")) {
            printDivider();
            switch (inputs[0]) {
            case "":
                System.out.println("Please enter either a task to do, mark/unmark <task number> or list");
                break;
            case "list":
                printToDoList();
                break;
            case "mark":
                if (inputs.length > 1) {
                    markTask(true, inputs[1]);
                }
                else {
                    System.out.println("Please specify a task number to mark.");
                }
                break;
            case "unmark":
                if (inputs.length > 1) {
                    markTask(false, inputs[1]);
                }
                else {
                    System.out.println("Please specify a task number to mark.");
                }
                break;
            default:
                saveInput(input);
                break;
            }
            printDivider();
            System.out.print(" > ");

            input = in.nextLine();
            inputs = input.split(" ");
        }
        printFarewell();
    }

    public static void main(String[] args) {
        printGreet();
        processInput();
    }
}
