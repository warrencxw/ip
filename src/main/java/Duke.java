import java.util.Scanner;

public class Duke {
    // Member attributes
    private static int inputCount = 0;
    private static final int MAX_COUNT = 100;
    private static String[] userInputs = new String[MAX_COUNT];

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
     * Takes the provided input string and saves it into the array "userInputs"
     * If the number of inputs exceed MAX_COUNT, the input string will not be saved.
     */
    public static void saveInput(String input) {
        if (inputCount >= MAX_COUNT) {
            System.out.println("We have reached the limit of " + MAX_COUNT +
                    " items that can be added to the list.\n" +
                    "The last entry, \"" + input + "\", will not be added to the list.");
        }
        else {
            userInputs[inputCount] = input;
            inputCount++;
            System.out.println("\"" + input + "\" has been added into the list.");
        }
    }

    /**
     *  Prints out the full list of inputs given by the user
     */
    public static void printInputList() {
        for (int i = 0; i < inputCount; i++) {
            System.out.println((i + 1) + ". " + userInputs[i]);
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

        while (!input.equals("bye")) {
            printDivider();
            switch (input) {
            case "list":
                printInputList();
                break;
            default:
                saveInput(input);
                break;
            }
            printDivider();
            System.out.print(" > ");
            input = in.nextLine();
        }
        printFarewell();
    }

    public static void main(String[] args) {
        printGreet();
        processInput();
    }
}
