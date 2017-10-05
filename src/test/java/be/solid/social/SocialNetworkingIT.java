package be.solid.social;

import be.solid.social.console.ConsoleAdapter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static be.solid.social.TestScenarios.commandLinePosts;
import static be.solid.social.TestScenarios.withExitCommand;
import static java.util.stream.Collectors.joining;


@DisplayName("Integration test for the social networking application")
class SocialNetworkingIT {

    private static final String LINE_SEPARATOR = "\r\n";
    private SocialNetworkApplication application;
    private ByteArrayOutputStream byteArrayOutputStream;

    @BeforeEach
    void setUp() {
        byteArrayOutputStream = new ByteArrayOutputStream();
        PrintStream outputStream = new PrintStream(byteArrayOutputStream);

        final String input = buildConsoleInput();
        buildApplication(outputStream, input);
    }

    @Test()
    @DisplayName("Run basic scenarios")
    void justRun() {
        application.run();
        System.out.println("=========================");
        final String printedMessages = byteArrayOutputStream.toString();
        System.out.println(printedMessages);
    }

    private String buildConsoleInput() {
        return withExitCommand(commandLinePosts()).stream()
                                                  .collect(joining(LINE_SEPARATOR));
    }

    private void buildApplication(PrintStream outputStream, String input) {
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        application = new SocialNetworkApplication(outputStream, new ConsoleAdapter(inputStream, outputStream));
    }
}
