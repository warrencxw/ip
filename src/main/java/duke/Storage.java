package duke;

import duke.task.TaskList;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

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
     * Loads from save.csv, all tasks that were previously saved, into TaskList
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
            }
        }
    }

    /**
     * Save into save.csv, all tasks that are currently in TaskList, as a CSV file
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

    public Storage() {
        saveFileName = "save.csv";
        saveFilePath = PATH_STRING_DATA_FOLDER + File.separator + saveFileName;
        saveFile = new File(saveFilePath);
        ui = new Display();
    }

    public Storage(String saveFileName, Display ui) {
        this.saveFileName = saveFileName;
        this.saveFilePath = PATH_STRING_DATA_FOLDER + File.separator + saveFileName;
        this.saveFile = new File(saveFilePath);
        this.ui = ui;
    }
}
