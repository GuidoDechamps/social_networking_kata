package be.solid.social.console;

import java.util.Optional;

public class ConsoleAdapter {

    public void processCommand(String line) {
        final Optional<Command> command = CommandParser.parseCommand(line);
        System.out.println("[SNA] i read: " + line);
        command.ifPresent(x -> System.out.println("[SNA] parsed to command: " + x.toString()));
    }

    private void launchCommand(Command c) {

    }
}
