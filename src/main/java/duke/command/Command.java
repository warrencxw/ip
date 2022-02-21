package duke.command;

import duke.Display;
import duke.Storage;
import duke.task.TaskList;

/**
 * Represents a command that the user has specified to carry out, along with its specified arguments
 */
public abstract class Command {
    public enum CommandType {
        EMPTY, HELP, LIST, MARK, TODO, DEADLINE, EVENT, FIND, DELETE, CLEAR, EXIT, UNRECOGNISED
    }

    // Member attributes
    protected String commandArgs;

    /**
     * Runs the command specified by the user according to its type, along with the specified argument
     *
     * @param ui      A Display object to manage printing of errors and other messages
     * @param tasks   A TaskList object with which the command may process or modify
     * @param storage A Storage object to manage the save file of the specified TaskList object if necessary
     */
    public abstract void run(Display ui, TaskList tasks, Storage storage);

    /**
     * Standard constructor, creates a Command with its accompanying arguments.
     *
     * @param commandArgs arguments that complement the command to function
     */
    public Command(String commandArgs) {
        this.commandArgs = commandArgs;
    }

    /**
     * Default constructor, creates a Command without any arguments.
     */
    public Command() {
        this.commandArgs = "";
    }
}
