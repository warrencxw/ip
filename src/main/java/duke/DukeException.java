package duke;

/**
 * Represents any exceptions that may be encountered by the program.
 */
public class DukeException extends Exception {
    public DukeException(String errorMessage) {
        super(errorMessage);
    }
}
