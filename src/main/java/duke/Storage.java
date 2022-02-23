package duke;

import duke.task.TaskList;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

/**
 * Represents a save file manager that saves a TaskList object or loads a TaskList object into a CSV file,
 * saved in the 'data' folder, with the name identified as per <code>saveFileName</code> when instantiated.
 */
public class Storage {
    // Member objects
    private File saveFile;
    public String saveFileName;
    private String saveFilePath;
    private Display ui;

    // String Constants
    private static final String PATH_STRING_DATA_FOLDER = "data";
    private static final String LINE_SEPARATOR = System.getProperty("line.separator");


    // TODO: CREATE A TEST FOR LOAD

    /**
     * Checks if a save file already exist on the system
     * If toCreate is true, creates the file structure and save file for saving TaskList data as it updates
     *
     * @param toCreate Tells the method whether to create the files for saving or only to check if save exists
     * @return Returns true if the save file exists, false otherwise
     * @throws IOException If File.exists(), File.mkdir(), File.createNewFile() faces exceptions.
     */
    private boolean saveExists(boolean toCreate) throws IOException {
        File directory = new File(PATH_STRING_DATA_FOLDER);
        if (!directory.exists()) {
            if (!toCreate) {
                return false;
            }

            boolean success = directory.mkdir();
            if (!success) {
                ui.printError(Display.ErrorType.FILE_CREATION_FAILED);
                return false;
            }
        }

        if (!saveFile.exists()) {
            if (!toCreate) {
                return false;
            }

            boolean success = saveFile.createNewFile();
            if (!success) {
                ui.printError(Display.ErrorType.FILE_CREATION_FAILED);
                return false;
            }
        }
        return true;
    }

    /**
     * Loads from a saved CSV file at <code>saveFilePath</code>, all tasks that were previously saved, into 
     * the TaskList object passed into this method. If a file does not exist, the method will terminate and 
     * <code>tasks</code> remains empty. A CSV file will not be created.
     * 
     * @param tasks the TaskList object in which the save file is loaded into
     */
    public void loadSave(TaskList tasks) {
        boolean saveExists = false;
        try {
            saveExists = saveExists(false);
        } catch (IOException exception) {
            exception.printStackTrace();
            ui.printError(Display.ErrorType.SAVE_LOAD_FAILED);
            return;
        }

        if (!saveExists) {
            return;
        }

        Scanner saveIn;
        try {
            saveIn = new Scanner(saveFile);
        } catch (FileNotFoundException exception) {
            exception.printStackTrace();
            ui.printError(Display.ErrorType.SAVE_LOAD_FAILED);
            return;
        }

        String inputLine;
        while (saveIn.hasNext()) {
            inputLine = saveIn.nextLine();
            String[] csvRecordEntries = inputLine.split(Duke.REGEX_PATTERN_CSV_DELIMITER);
            try {
                tasks.addTaskFromCSVRecord(csvRecordEntries);
            } catch (DukeException exception) {
                ui.printlnMessage(exception.getMessage());
                return;
            } catch (DateTimeParseException exception) {
                exception.printStackTrace();
                return;
            }
        }
    }

    /**
     * Save into <code>saveFilePath</code>, all tasks that are currently in the given TaskList object, as a CSV file.
     * Creates a CSV file if it does not yet exist.
     * 
     * @param tasks The TaskList object with which all tasks within would be saved from
     */
    public void saveChanges(TaskList tasks) {
        boolean saveExists;
        try {
            saveExists = saveExists(true);
        } catch (IOException exception) {
            exception.printStackTrace();
            ui.printError(Display.ErrorType.FILE_CREATION_FAILED);
        }

        FileWriter fw;
        try {
            fw = new FileWriter(saveFilePath);
        } catch (IOException exception) {
            exception.printStackTrace();
            ui.printError(Display.ErrorType.SAVE_WRITE_FAILED);
            return;
        }

        String[] saveStrings = tasks.getSavableCSVStrings();
        try {
            for (String saveString : saveStrings) {
                fw.write(saveString + LINE_SEPARATOR);
            }
            fw.close();
        } catch (IOException exception) {
            exception.printStackTrace();
            ui.printError(Display.ErrorType.SAVE_WRITE_FAILED);
        }
    }

    /**
     * Default constructor, creates a Storage object that saves into and loads from a default file "save.csv"
     */
    public Storage() {
        saveFileName = "save.csv";
        saveFilePath = PATH_STRING_DATA_FOLDER + File.separator + saveFileName;
        saveFile = new File(saveFilePath);
        ui = new Display();
    }

    /**
     * Alternative constructor, creates a Storage object that saves into and loads from a file with the name identified
     * in <code>saveFileName</code>. Also reads in a Display object to manage error printing.
     * 
     * @param saveFileName name of CSV file to be used for saving and loading TaskList objects
     * @param ui           a Display object to manage printing of errors and other messages
     */
    public Storage(String saveFileName, Display ui) {
        this.saveFileName = saveFileName;
        this.saveFilePath = PATH_STRING_DATA_FOLDER + File.separator + saveFileName;
        this.saveFile = new File(saveFilePath);
        this.ui = ui;
    }
}
