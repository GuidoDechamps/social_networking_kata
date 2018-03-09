package be.solid.social.main;

import be.solid.social.MessageData;
import be.solid.social.console.CommandTokens;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.List;
import java.util.stream.Collectors;

import static be.solid.social.TestScenarios.*;
import static java.time.Clock.systemUTC;
import static java.util.List.of;
import static java.util.stream.Collectors.joining;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


@DisplayName("Integration test for the social networking application")
class SocialNetworkingIT {

    private static final String LINE_SEPARATOR = "\r\n";
    private SocialNetwork application;
    private ByteArrayOutputStream byteArrayOutputStream;
    private MutableStringInputStream inputStream;

    @BeforeEach
    void setUp() {
        inputStream = new MutableStringInputStream();
        byteArrayOutputStream = new ByteArrayOutputStream();
        application = buildApplication();
    }

    @Test()
    @DisplayName("View user timelines")
    void runViewUserTimeLines() {
        runApplicationWithCommands(postsGivenFromCommandLine());
        sequenceOfPosts().forEach(x -> {
            runApplicationWithCommand(x.sender);
            final List<String> commandResults = getCommandResults();
            validateExpectedMessageInCommandResult(x, commandResults);
        });
    }

    @Test()
    @DisplayName("Follow command scenarios")
    void runFollowCommands() {
        runApplicationWithCommands(followsGivenFromCommandLine());

        assertTrue(getCommandResults().isEmpty());
    }

    @Test()
    @DisplayName("Wall command scenarios")
    void runWallCommands() {
        runApplicationWithCommands(postsGivenFromCommandLine());
        runApplicationWithCommands(wallCommandFromCommandLine());

        assertFalse(getCommandResults().isEmpty());
    }

    @Test()
    @DisplayName("Post command scenarios")
    void runPostsCommands() {
        runApplicationWithCommands(postsGivenFromCommandLine());

        assertTrue(getCommandResults().isEmpty());
    }

    @Test()
    @DisplayName("Invalid command does not crash")
    void invalidCommand() {
        inputStream.stream("invalid command given");
        application.run();
        final List<String> commandResults = getCommandResults();
        assertTrue(commandResults.isEmpty());
    }

    private void validateExpectedMessageInCommandResult(MessageData expectedMessage, List<String> commandResults) {
        assertTrue(commandResults.contains(expectedMessage.message), buildMessageNotFound(expectedMessage, commandResults));
    }

    private String buildMessageNotFound(MessageData expectedMessage, List<String> commandResults) {
        return "Message [" + expectedMessage.message + "] was not found in output [" + commandResults + "]";
    }

    private void runApplicationWithCommands(List<String> commands) {
        inputStream.stream(buildConsoleInput(commands));
        application.run();
    }

    private void runApplicationWithCommand(String command) {
        runApplicationWithCommands(of(command));
    }

    private List<String> getCommandResults() {
        return extractCommandResults(byteArrayOutputStream.toString());
    }

    private List<String> extractCommandResults(String printedMessages) {
        final String withoutApplicationMessages = removeApplicationMessages(printedMessages);

        return of(withoutApplicationMessages.split("\\n")).stream()
                                                          .filter(x -> !x.isEmpty())
                                                          .map(String::trim)
                                                          .collect(Collectors.toList());
    }

    private String removeApplicationMessages(String printedMessages) {
        return printedMessages.replaceAll("\\[.*\\]", "")
                              .trim();
    }

    private SocialNetwork buildApplication() {
        final PrintStream outputStream = new PrintStream(byteArrayOutputStream);
        return SocialNetworkFactory.create(systemUTC(), inputStream, outputStream);
    }

    private String buildConsoleInput(List<String> commands) {
        return withExitCommand(commands).stream()
                                        .collect(joining(LINE_SEPARATOR));
    }

    class MutableStringInputStream extends InputStream {
        private static final String END_INPUT_TOKEN = CommandTokens.EXIT + LINE_SEPARATOR;
        private StringReader reader = new StringReader(END_INPUT_TOKEN);

        @Override
        public int read() throws IOException {
            return reader.read();
        }


        public void stream(String dataToStream) {
            reader = new StringReader(dataToStream + LINE_SEPARATOR + END_INPUT_TOKEN);
        }
    }

}
