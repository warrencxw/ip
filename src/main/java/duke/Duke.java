package duke;

import duke.command.Command;
import duke.command.ExitCommand;
import duke.task.TaskList;

import java.util.Scanner;

/**
 * The starting point of all other processes in this application.
 * Instantiate objects of other classes so that the application can function correctly.
 */
public class Duke {
    // Key objects
    public TaskList tasks;
    public Storage storage;
    public Display ui;
    private Scanner in;

    // Regex patterns
    public static final String REGEX_PATTERN_CSV_DELIMITER = "[ ]*,[ ]*";
    public static final String CSV_DELIMITER = ",";

    // Misc Constants
    public static final String INPUT_PREPEND = " > ";

    /**
     * Reads in input from standard input and processes the input to determine what commands to carry out.
     * Repeats process until exit/quit/bye commands are called.
     */
    public void processInputLoop() {
        String input;
        Command command;

        do {
            input = ui.getNextLineWithPrepend(INPUT_PREPEND);
            ui.printDivider();
            command = Parser.getCommand(input);
            command.run(ui, tasks, storage);
            ui.printDivider();
        } while (!(command instanceof ExitCommand));
    }

    /**
     * Instantiates a new instance of the application with a specific file name for the save file
     *
     * @param saveFileName file name of CSV file to which all modifications to tasks is saved into
     */
    public Duke(String saveFileName) {
        in = new Scanner(System.in);
        ui = new Display(in);
        tasks = new TaskList(ui);
        storage = new Storage(saveFileName, ui);
        storage.loadSave(tasks);
    }

    /**
     * Begins the process loop of the program and accept inputs from the user
     */
    public void run() {
        ui.printGreetingMessage();
        processInputLoop();
    }
    
    public static void main(String[] args) {
        new Duke("save.csv").run();
    }
}
