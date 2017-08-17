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
    @MethodSource("commandLinePosts")
    void postParsing(String inputLine) {
        testParseToCommand(inputLine, Posting.class);
    }

    @ParameterizedTest()
    @DisplayName("View User TimeLine")
    @MethodSource("allUsers")
    void viewTimeLine(String inputLine) {
        testParseToCommand(inputLine, ViewTimeLine.class);
    }

    @ParameterizedTest()
    @DisplayName("View ViewWall")
    @MethodSource("allUsers")
    void viewWall(String inputLine) {
        testParseToCommand(inputLine, ViewWall.class);
    }

    private static List<String> commandLinePosts() {
        return TestScenarios.commandLinePosts();
    }
    private static List<String> wallCommandForAllUsers() {
        return TestScenarios.commandLineWall();
    }

    private static List<String> allUsers() {
        return TestScenarios.users();
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