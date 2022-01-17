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
    public static void greet() {
        printDivider();
        String logo = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";
        System.out.println(logo + "Hello! I'm Duke\nWhat can I do for you?");
        printDivider();
    }

    /**
     * Prints out the farewell message to standard output
     */
    public static void farewell() {
        printDivider();
        System.out.println("Bye. Hope to see you again soon!");
        printDivider();
    }

    public static void main(String[] args) {
        greet();
        farewell();
    }
}
