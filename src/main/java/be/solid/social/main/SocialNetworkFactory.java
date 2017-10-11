package be.solid.social.main;

import be.solid.social.console.ConsoleAdapter;
import be.solid.social.impl.Messages;
import be.solid.social.usecase.SocialNetworkUseCases;

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
        final SocialNetworkUseCases socialNetworkUseCases = new SocialNetworkUseCases(messages, messages);
        final ConsoleAdapter consoleAdapter = new ConsoleAdapter(socialNetworkUseCases, in, outputStream);
        return new SocialNetwork(consoleAdapter);
    }
}
