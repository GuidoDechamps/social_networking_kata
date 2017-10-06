package be.solid.social.main;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.List;

import static be.solid.social.TestScenarios.allCommandLines;
import static be.solid.social.TestScenarios.withExitCommand;
import static java.time.Clock.systemUTC;
import static java.util.stream.Collectors.joining;
import static org.junit.jupiter.api.Assertions.assertFalse;


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
        System.out.println(byteArrayOutputStream);
        final List<String> commandResults = extractCommandResults(byteArrayOutputStream.toString());
        assertFalse(commandResults.isEmpty());
    }

    private List<String> extractCommandResults(String printedMessages) {
        final String withoutApplicationMessages = printedMessages.replaceAll("\\[.*\\]", "");

        return List.of(withoutApplicationMessages.split("\\n"));
    }

    private SocialNetwork buildApplication() {
        final PrintStream outputStream = new PrintStream(byteArrayOutputStream);
        final String input = buildConsoleInput();
        final InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        return SocialNetworkFactory.create(systemUTC(), inputStream, outputStream);
    }

    private String buildConsoleInput() {
        return withExitCommand(allCommandLines()).stream()
                                                 .collect(joining(LINE_SEPARATOR));
    }

}
