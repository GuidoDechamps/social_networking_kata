package be.solid.social.console;

import be.solid.social.TestScenarios;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Console Command Parser Test")
class CommandParserTest {

    @ParameterizedTest()
    @DisplayName("Publish single message")
    @MethodSource("allPostsToBeMade")
    void postParsing(String inputLine) {
        testParseToCommand(inputLine, Posting.class);
    }

    @ParameterizedTest()
    @DisplayName("View TimeLine")
    @MethodSource("allUsers")
    void viewTimeLine(String inputLine) {
        testParseToCommand(inputLine, ViewTimeLine.class);
    }

    private static List<String> allPostsToBeMade() {
        return TestScenarios.commandLinePosts();
    }

    private static List<String> allUsers() {
        return TestScenarios.commandLinePosts();
    }

    private void testParseToCommand(String inputLine, Class<? extends Command> commandClazz) {
        final Optional<Command> command = CommandParser.parseCommand(inputLine);
        final Command c = command.orElseGet(throwUnableToParseException(inputLine));
        assertTrue(commandClazz.isInstance(c), notAPostCommand(inputLine, commandClazz));
    }

    private String notAPostCommand(String inputLine, Class<?> clazz) {
        return inputLine + " could not be parsed to a " + clazz.getSimpleName() + " command.";
    }

    private Supplier<Command> throwUnableToParseException(String inputLine) {
        return () -> {
            throw new RuntimeException("The command " + inputLine + " could not be parsed");
        };
    }
}