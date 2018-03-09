package be.solid.social.main;

import be.solid.social.console.CommandParser;
import be.solid.social.console.ConsoleAdapter;
import be.solid.social.console.ConsoleMessagePrinter;
import be.solid.social.impl.Messages;
import be.solid.social.usecase.CommandFactory;

import java.io.InputStream;
import java.io.PrintStream;
import java.time.Clock;

class SocialNetworkFactory {

    private SocialNetworkFactory() {
    }

    static SocialNetwork create() {
        return create(Clock.systemUTC(), System.in, System.out);
    }

    static SocialNetwork create(Clock clock, InputStream in, PrintStream outputStream) {
        final Messages messages = new Messages(clock);
        final ConsoleMessagePrinter messagePrinter = new ConsoleMessagePrinter(outputStream);
        final CommandParser commandParser = new CommandParser(new CommandFactory(messages, messagePrinter, messages));
        final ConsoleAdapter consoleAdapter = new ConsoleAdapter(in, messagePrinter, commandParser);
        return new SocialNetwork(consoleAdapter);
    }
}
