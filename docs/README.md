# User Guide
Duke is a **desktop application for managing and keeping track of tasks,
optimised for use via a Command Line Interface** (CLI).
As Duke is a CLI application, it can help you manage your tasks much faster if you can type fast.

---

## Quick Start
1. Ensure that you have `Java 11` or above installed on your computer.
2. Download the latest release of `Warren's version of Duke` from Github [here](https://github.com/warrencxw/ip/releases).
3. Copy the file to a folder that you want to use as the _home folder_ for the application.
   Do note that a new folder `data` would be created in the same folder,
   along with the save file `save.csv` within that folder, to save all your tasks.
4. Open your command line interface or terminal _(e.g. `cmd`)_ and change the working directory to the _home folder_.
5. Run the program by using the command `java -jar ip.jar`. An interface similar to the one below should appear.
   <!-- Add screenshots here -->
6. Type any commands you would like and press the _enter_ key to execute the command.
   The following are some examples to start using the application.
   - `help`: Lists all the commands available in the application.
   - `list`: Lists all saved tasks.
   - `deadline Homework /by 2022-03-04`: Adds a task named "Homework" with a due date of 4 March 2022.
   - `mark 1`: Marks the first task in the list as complete.
- You may refer to [Features](#features) below for a list of all the commands and their details.

---

## Features 
> **Notes regarding the command format:**
> - Words in `<ANGLE BRACKETS>` are parameters to be supplied by the user<br>
>   e.g. in `find <SUBSTRING>`, `<SUBSTRING>` is a parameter which can be replaced as in `find coding assignment`.
> - Parameters, when given to commands that do not accept parameters (e.g. `list`, `help`) will be ignored.<br>
>   e.g. an input of `list apple` will be interpreted as just `list`
> - Aliases are alternative ways to perform the same command, and can be used to replace the command word.
> - Commas are ***not*** allowed to be used in any of the parameters.
### List of Features
- [Listing all tasks](#listing-all-tasks--list) `list`
- [Displaying the help menu](#displaying-the-help-menu--help) `help`
- [Creating a todo task](#creating-a-simple-todo-task--todo) `todo`
- [Creating an event task](#creating-an-event-task--event) `event`
- [Creating a deadline task](#creating-a-deadline-task--deadline) `deadline`
- [Marking a task as complete](#marking-a-task-as-complete--mark) `mark`
- [Marking a task as incomplete](#marking-a-task-as-incomplete--unmark) `unmark`
- [Finding a task](#finding-a-task-by-searching-with-substring--find) `find`
- [Displaying help menu](#displaying-the-help-menu--help) `help`
- [Deleting a task](#deleting-a-single-task--delete) `delete`
- [Clearing all tasks](#clearing-all-tasks--clear) `clear`
- [Exiting the application](#exiting-the-application--bye) `bye`
- [Saving data](#saving-data)
- [Loading data](#loading-data)
- [Editing the save file](#editing-the-save-file)

### Feature-ABC

Description of the feature.

### Feature-XYZ

Description of the feature.

## Usage

### `Keyword` - Describe action

Describe the action and its outcome.

Example of usage: 

`keyword (optional arguments)`

Expected outcome:

Description of the outcome.

```
expected output
```
