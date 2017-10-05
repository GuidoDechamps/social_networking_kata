package be.solid.social.console;

import be.solid.social.usecase.Command;
import be.solid.social.usecase.SocialNetworkUseCases;

import java.io.InputStream;
import java.io.PrintStream;
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
        outputStream.println("[i read: " + line + "]");
        final Optional<Command> command = CommandParser.parseCommand(line);
        command.ifPresent(x -> x.execute(socialNetworkUseCases));
        outputStream.println("[executed: " + line + "]");
    }


}
