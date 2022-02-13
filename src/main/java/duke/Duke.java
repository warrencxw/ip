package duke;

import duke.task.TaskList;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;

public class Duke {
    // Regex patterns
    public static final String REGEX_PATTERN_WHITESPACES = "\\s";
    public static final String REGEX_PATTERN_CSV_DELIMITER = "[ ]*,[ ]*";
    public static final String CSV_DELIMITER = ",";

    // Misc Constants
    public static final String INPUT_PREPEND = " > ";
    private static final String PATH_STRING_DATA_FOLDER = "data";
    private static final String PATH_STRING_SAVE_FILE = PATH_STRING_DATA_FOLDER + File.separator + "save.csv";
    private static final String LINE_SEPARATOR = System.getProperty("line.separator");
    
    private static boolean saveExists(boolean toCreate) throws IOException {
        File directory = new File(PATH_STRING_DATA_FOLDER);
        if (!directory.exists()) {
            if (!toCreate) {
                return false;
            }
            
            boolean success = directory.mkdir();
            if (!success) {
                // TODO: Print error message
                return false;
            }
        }

        File saveFile = new File(PATH_STRING_SAVE_FILE);
        if (!saveFile.exists()) {
            if (!toCreate) {
                return false;
            }
            
            boolean success = saveFile.createNewFile();
            if (!success) {
                // TODO: Print error message
                return false;
            }
        }
        return true;
    }
    
    private static void loadSave() {
        boolean saveExists = false;
        try {
            saveExists = saveExists(false);
        } catch (IOException exception) {
            exception.printStackTrace();
            // TODO: Include error message
        }
        
        if (!saveExists) {
            return;
        }

        File saveFile;
        Scanner saveIn;
        try {
            saveFile = new File(PATH_STRING_SAVE_FILE);
            saveIn = new Scanner(saveFile);
        } catch (FileNotFoundException exception) {
            exception.printStackTrace();
            // TODO: Include error message
            return;
        }
        
        String inputLine; 
        while (saveIn.hasNext()) {
            inputLine = saveIn.nextLine();
            String[] csvRecordEntries = inputLine.split(REGEX_PATTERN_CSV_DELIMITER);
            try {
                TaskList.addTaskFromCSVRecord(csvRecordEntries);
            } catch (DukeException exception) {
                System.out.println(exception.getMessage());
                return;
            }
        }
    }
    
    private static void saveChanges() {
        boolean saveExists;
        try {
            saveExists = saveExists(true);
        } catch (IOException exception) {
            exception.printStackTrace();
            // TODO: Include error message
        }
        
        FileWriter fw;
        try {
            fw = new FileWriter(PATH_STRING_SAVE_FILE);
        } catch (IOException exception) {
            exception.printStackTrace();
            // TODO: Include error message
            return;
        }
        
        String[] saveStrings = TaskList.getSavableCSVStrings();
        try {
            for (String saveString : saveStrings) {
                fw.write(saveString + LINE_SEPARATOR);
            }
            fw.close();
        } catch (IOException exception) {
            exception.printStackTrace();
            // TODO: Include error message
        }
    }

    /**
     * Reads in input from standard input and processes the input to determine
     * what operations to carry out by calling other helper methods.
     * Repeats process until exit/quit/bye commands are called.
     */
    public static void processInputLoop() {
        Scanner in = new Scanner(System.in);
        System.out.print(INPUT_PREPEND);
        String input = in.nextLine().trim();
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
                saveChanges();
                break;
            // MARK TASK AS NOT DONE
            case "unmark":
                TaskList.processInputAndMarkTask(false, inputs);
                saveChanges();
                break;
            // CREATE NEW TODO
            case "todo":
                TaskList.saveInputAsTask(inputs, TaskList.TaskType.TODO);
                saveChanges();
                break;
            // CREATE NEW DEADLINE
            case "deadline":
                TaskList.saveInputAsTask(inputs, TaskList.TaskType.DEADLINE);
                saveChanges();
                break;
            // CREATE NEW EVENT
            case "event":
                TaskList.saveInputAsTask(inputs, TaskList.TaskType.EVENT);
                saveChanges();
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
        loadSave();
        Display.printGreetingMessage();
        processInputLoop();
    }
}
