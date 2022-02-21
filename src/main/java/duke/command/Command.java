package duke.command;

import duke.Display;
import duke.Storage;
import duke.task.TaskList;

public abstract class Command {
    public enum CommandType {
        EMPTY, HELP, LIST, MARK, TODO, DEADLINE, EVENT, FIND, DELETE, CLEAR, EXIT, UNRECOGNISED
    }
    
    // Member attributes
    protected String commandArgs;
    
    public abstract void run(Display ui, TaskList tasks, Storage storage);
    
    public Command(String commandArgs) {
        this.commandArgs = commandArgs;
    }
    
    public Command() {
        this.commandArgs = "";
    }
}
