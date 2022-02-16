package duke;

import duke.task.TaskList;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class SaveManager {
    public static final String SAVE_FILE_NAME = "save.csv";
    private static final String PATH_STRING_DATA_FOLDER = "data";
    private static final String PATH_STRING_SAVE_FILE = PATH_STRING_DATA_FOLDER + File.separator + SAVE_FILE_NAME;
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
    private static boolean saveExists(boolean toCreate) throws IOException {
        File directory = new File(PATH_STRING_DATA_FOLDER);
        if (!directory.exists()) {
            if (!toCreate) {
                return false;
            }
            
            boolean success = directory.mkdir();
            if (!success) {
                Display.printError(Display.ErrorType.FILE_CREATION_FAILED);
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
                Display.printError(Display.ErrorType.FILE_CREATION_FAILED);
                return false;
            }
        }
        return true;
    }

    /**
     * Loads from save.csv, all tasks that were previously saved, into TaskList
     */
    static void loadSave() {
        boolean saveExists = false;
        try {
            saveExists = saveExists(false);
        } catch (IOException exception) {
            exception.printStackTrace();
            Display.printError(Display.ErrorType.SAVE_LOAD_FAILED);
            return;
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
            Display.printError(Display.ErrorType.SAVE_LOAD_FAILED);
            return;
        }
        
        String inputLine; 
        while (saveIn.hasNext()) {
            inputLine = saveIn.nextLine();
            String[] csvRecordEntries = inputLine.split(Duke.REGEX_PATTERN_CSV_DELIMITER);
            try {
                TaskList.addTaskFromCSVRecord(csvRecordEntries);
            } catch (DukeException exception) {
                System.out.println(exception.getMessage());
                return;
            }
        }
    }

    /**
     * Save into save.csv, all tasks that are currently in TaskList, as a CSV file
     */
    static void saveChanges() {
        boolean saveExists;
        try {
            saveExists = saveExists(true);
        } catch (IOException exception) {
            exception.printStackTrace();
            Display.printError(Display.ErrorType.FILE_CREATION_FAILED);
        }
        
        FileWriter fw;
        try {
            fw = new FileWriter(PATH_STRING_SAVE_FILE);
        } catch (IOException exception) {
            exception.printStackTrace();
            Display.printError(Display.ErrorType.SAVE_WRITE_FAILED);
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
            Display.printError(Display.ErrorType.SAVE_WRITE_FAILED);
        }
    }
}
