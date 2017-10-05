package be.solid.social.main;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.time.Clock;

import static be.solid.social.TestScenarios.allCommandLines;
import static be.solid.social.TestScenarios.withExitCommand;
import static java.util.stream.Collectors.joining;


@DisplayName("Integration test for the social networking application")
class SocialNetworkingIT {

    private static final String LINE_SEPARATOR = "\r\n";
    private SocialNetwork application;
    private ByteArrayOutputStream byteArrayOutputStream;

    @BeforeEach
    void setUp() {
        byteArrayOutputStream = new ByteArrayOutputStream();
        application = buildApplication();
    }

    @Test()
    @DisplayName("Run basic scenarios")
    void justRun() {
        application.run();
        System.out.println("=========================");
        final String printedMessages = byteArrayOutputStream.toString();
        System.out.println(printedMessages);
    }

    private SocialNetwork buildApplication() {
        final PrintStream outputStream = new PrintStream(byteArrayOutputStream);
        final String input = buildConsoleInput();
        final InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        return SocialNetworkFactory.create(Clock.systemUTC(), inputStream, outputStream);
    }

    private String buildConsoleInput() {
        return withExitCommand(allCommandLines()).stream()
                                                  .collect(joining(LINE_SEPARATOR));
    }

}
