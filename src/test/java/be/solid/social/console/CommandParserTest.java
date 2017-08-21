package be.solid.social.console;

import be.solid.social.TestScenarios;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.fail;

@DisplayName("Console Command Parser Test")
class CommandParserTest {

    @ParameterizedTest()
    @DisplayName("Parse command for publishing single message")
    @MethodSource("commandLinePosts")
    void postParsing(String inputLine) {
        testParseToCommand(inputLine, Posting.class);
    }

    @ParameterizedTest()
    @DisplayName("Parse command for viewing User TimeLine")
    @MethodSource("allUsers")
    void viewTimeLine(String inputLine) {
        testParseToCommand(inputLine, ViewTimeLine.class);
    }

    @ParameterizedTest()
    @DisplayName("Parse command for viewing Wall")
    @MethodSource("wallCommandForAllUsers")
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
        final Optional<Command> parseResult = CommandParser.parseCommand(inputLine);
        final Command command = parseResult.orElseGet(throwUnableToParseException(inputLine));
        if (isWrongCommand(commandClazz, command)) fail(buidlWrongCommandMessage(inputLine, commandClazz, command));
    }

    private boolean isWrongCommand(Class<? extends Command> commandClazz, Command command) {
        return !commandClazz.isInstance(command);
    }

    private String buidlWrongCommandMessage(String inputLine, Class<? extends Command> commandClazz, Command command) {
        final String notCorrectCommandMessage = buildNotCorrectCommandMessage(inputLine, commandClazz);
        final String actualComandMessage = buildActualComandMessage(command);
        return notCorrectCommandMessage + actualComandMessage;
    }

    private String buildActualComandMessage(Command command) {
        return "It was a " + command.getClass()
                                    .getSimpleName() + " command.";
    }

    private String buildNotCorrectCommandMessage(String inputLine, Class<?> clazz) {
        return inputLine + " could not be parsed to a " + clazz.getSimpleName() + " command.";
    }

    private Supplier<Command> throwUnableToParseException(String inputLine) {
        return () -> {
            throw new RuntimeException("The command " + inputLine + " could not be parsed");
        };
    }
}