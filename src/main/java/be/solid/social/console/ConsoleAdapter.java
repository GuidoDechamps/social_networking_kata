package be.solid.social.console;

import be.solid.social.usecase.Usecase;

import java.io.InputStream;
import java.util.Optional;
import java.util.Scanner;

import static be.solid.social.console.CommandTokens.EXIT;

//TODO refactor class. to many responsibilities
public class ConsoleAdapter {

    private final ConsoleMessagePrinter messagePrinter;
    private final InputStream inputStream;
    private final CommandParser commandParser;
    private SCAN_STATE state;


    public ConsoleAdapter(InputStream inputStream, ConsoleMessagePrinter messagePrinter, CommandParser commandParser) {
        this.messagePrinter = messagePrinter;
        this.inputStream = inputStream;

        this.commandParser = commandParser;
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
        final Optional<Usecase> usecase = commandParser.parseCommand(line);
        usecase.ifPresentOrElse(this::executeUsecase, printNoCommand(line));
    }

    private Runnable printNoCommand(String line) {
        return () -> messagePrinter.noCommand(line);

    }

    private void executeUsecase(Usecase usecase) {
        usecase.execute();
    }


    enum SCAN_STATE {
        CONTINUE_SCANNING, EXIT;

        private boolean isContinueScanning() {
            return this.equals(SCAN_STATE.CONTINUE_SCANNING);
        }

    }
}
