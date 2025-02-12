package seedu.duke;

import seedu.duke.command.Command;
import seedu.duke.command.CommandNames;
import seedu.duke.exceptions.CommandNotAvailableException;
import seedu.duke.storage.Storage;

import java.util.ArrayList;
import java.util.Scanner;

public class Duke {
    /**
     * Main entry-point for the java.duke.Duke application.
     */
    public static void main(String[] args) {
        InputParser parser = new InputParser();
        Storage.load();
        Scanner input = new Scanner(System.in);
        Ui ui =  new Ui();

        System.out.println(ui.getWelcomeMsg());

        String userInput = input.nextLine().toLowerCase();
        while (!userInput.equals("bye")) {
            try {
                //Get command name and parameters
                CommandNames userCommandName = parser.getCommandName(userInput);
                ArrayList<String> parameters = parser.getParameters(userInput, userCommandName);

                //Switch to appropriate callback function and call function
                Command userCommand = userCommandName.getCallbackCommand();
                userCommand.execute(parameters);
                Storage.write("ingredient");
                Storage.write("dish");

            } catch (CommandNotAvailableException e) {
                System.out.println(ui.getInvalidCommandMsg());
            }
            userInput = input.nextLine();
        }
        System.out.println(ui.getExitMsg());
    }
}
