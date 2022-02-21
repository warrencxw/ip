package duke;

import duke.command.Command;
import duke.command.ExitCommand;
import duke.task.TaskList;

import java.util.Scanner;

public class Duke {
    // Key objects
    public TaskList tasks;
    public Storage storage;
    public Display ui;
    private Scanner in;
    
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
    public void processInputLoop() {
        String input;
        Command command;
        
        do {
            ui.printMessage(INPUT_PREPEND);
            input = ui.getNextLine();
            ui.printDivider();
            command = Parser.getCommand(input);
            command.run(ui, tasks, storage);
            ui.printDivider();
        } while (!(command instanceof ExitCommand));
    }

    public Duke(String saveFileName) {
        in = new Scanner(System.in);
        ui = new Display(in);
        tasks = new TaskList(ui);
        storage = new Storage(saveFileName, ui);
        storage.loadSave(tasks);
    }
    
    public void run() {
        ui.printGreetingMessage();
        processInputLoop();
    }
    
    public static void main(String[] args) {
        new Duke("save.csv").run();
    }
}
