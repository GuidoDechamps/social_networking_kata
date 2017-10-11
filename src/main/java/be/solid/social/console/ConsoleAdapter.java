package be.solid.social.console;

import be.solid.social.usecase.Command;
import be.solid.social.usecase.SocialNetworkUseCases;

import java.io.InputStream;
import java.util.Optional;
import java.util.Scanner;

import static be.solid.social.console.CommandTokens.EXIT;

//TODO refactor class. to many responsibilities
public class ConsoleAdapter {

    private final SocialNetworkUseCases socialNetworkUseCases;
    private final ConsoleMessagePrinter messagePrinter;
    private final InputStream inputStream;
    private SCAN_STATE state;


    public ConsoleAdapter(SocialNetworkUseCases socialNetworkUseCases, InputStream inputStream, ConsoleMessagePrinter messagePrinter) {
        this.socialNetworkUseCases = socialNetworkUseCases;
        this.messagePrinter = messagePrinter;
        this.inputStream = inputStream;

    }

    public void scanContinuously() {
        messagePrinter.printStartMessage();
        state = SCAN_STATE.CONTINUE_SCANNING;
        final Scanner scanner = new Scanner(inputStream);
        do {
            messagePrinter.printRequestForCommand();
            if (scanner.hasNextLine()) processInputLine(scanner.nextLine());
            else handleNoInputGiven();
        } while (state.isContinueScanning());

    }


    private void handleNoInputGiven() {
        messagePrinter.printNoInputReceived();
    }

    private void processInputLine(String line) {
        if (isExitCommand(line)) stopScanning();
        else processCommand(line);
    }

    private void stopScanning() {
        state = SCAN_STATE.EXIT;
    }


    private boolean isExitCommand(String line) {
        return line.trim()
                   .equalsIgnoreCase(EXIT);
    }


    private void processCommand(String line) {
        messagePrinter.printCommand(line);
        final Optional<Command> command = CommandParser.parseCommand(line);
        command.ifPresentOrElse(this::executeCommand, printNoCommand(line));
    }

    private Runnable printNoCommand(String line) {
        return () -> messagePrinter.noCommand(line);

    }

    private void executeCommand(Command command) {
        final Object result = command.execute(socialNetworkUseCases);
        messagePrinter.printResult(result);

    }


    enum SCAN_STATE {
        CONTINUE_SCANNING, EXIT;

        private boolean isContinueScanning() {
            return this.equals(SCAN_STATE.CONTINUE_SCANNING);
        }

    }
}
