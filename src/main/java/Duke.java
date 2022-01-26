import java.util.Scanner;

public class Duke {
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

    public static void processInput() {
        Scanner in = new Scanner(System.in);
        System.out.print(" > ");
        String input = in.nextLine();

        while (!input.equals("bye")) {
            printDivider();
            System.out.println(input);
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
