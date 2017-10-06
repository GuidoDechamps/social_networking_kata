package be.solid.social.console;

import be.solid.social.usecase.Command;
import be.solid.social.usecase.Event;
import be.solid.social.usecase.SocialNetworkUseCases;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import static be.solid.social.console.CommandTokens.EXIT;

public class ConsoleAdapter {

    private final SocialNetworkUseCases socialNetworkUseCases;
    private final InputStream inputStream;
    private final PrintStream outputStream;

    public ConsoleAdapter(SocialNetworkUseCases socialNetworkUseCases, InputStream inputStream, PrintStream outputStream) {
        this.socialNetworkUseCases = socialNetworkUseCases;
        this.inputStream = inputStream;
        this.outputStream = outputStream;
    }

    public void scanContinuously() {
        printStartMessage();
        final Scanner scanner = new Scanner(inputStream);
        while (true) {
            printRequestForCommand();

            final String line = scanner.nextLine();
            if (isExitCommand(line)) break;
            else processCommand(line);

        }
    }

    private boolean isExitCommand(String line) {
        return line.trim()
                   .equalsIgnoreCase(EXIT);
    }

    private void printRequestForCommand() {
        outputStream.println("");
        outputStream.println("[Please enter a command - exit to quit]");
    }


    private void processCommand(String line) {
        outputStream.println("[" + line + "]");
        final Optional<Command> command = CommandParser.parseCommand(line);
        command.ifPresent(this::executeCommand);
    }

    private void executeCommand(Command command) {
        final Object result = command.execute(socialNetworkUseCases);
        //TODO write elsewhere
        printResult(result);

    }

    private void printResult(Object result) {
        if (result == null) {
            outputStream.println("[executed command ");
        } else if (Event.class.isInstance(result)) {
            final Event event = Event.class.cast(result);
            outputStream.println(event.user);
        } else if (List.class.isInstance(result)) {
            final List events = List.class.cast(result);
            events.forEach(this::printResult);
        } else throw new RuntimeException("Unknown result type " + result.getClass()
                                                                         .getSimpleName());
    }

    private void printStartMessage() {
        outputStream.println("Commands : ");
        outputStream.println("- posting: <user name> -> <message>");
        outputStream.println("- reading: <user name>");
        outputStream.println("- following: <user name> follows <another user>");
        outputStream.println("- wall: <user name> wall");
        outputStream.println("- exit: quit application");
    }


}
