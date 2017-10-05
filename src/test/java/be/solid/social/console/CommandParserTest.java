package be.solid.social.console;

import be.solid.social.TestScenarios;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import static be.solid.social.console.CommandTokens.ARROW;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Console Command Parser Test")
class CommandParserTest {

    @ParameterizedTest()
    @DisplayName("Parse command for posting single message")
    @MethodSource("commandLinePosts")
    void postParsing(String inputLine) {
        final Command command = parseToCommand(inputLine);

        final Posting posting = convertToCommandType(command, inputLine, Posting.class);

        validatePostCommand(inputLine, posting);
    }

    @ParameterizedTest()
    @DisplayName("Parse command for users following users")
    @MethodSource("commandLineFollows")
    void followParsing(String inputLine) {
        final Command command = parseToCommand(inputLine);

        final Following posting = convertToCommandType(command, inputLine, Following.class);
        assertTrue(inputLine.startsWith(posting.user), "Invalid user " + posting.user);
        assertTrue(inputLine.endsWith(posting.subscriptionTopic), "Invalid user subscription :" + posting.subscriptionTopic);

    }

    @ParameterizedTest()
    @DisplayName("Parse command for reading/viewing a User TimeLine")
    @MethodSource("allUsers")
    void viewTimeLine(String inputLine) {
        final Command command = parseToCommand(inputLine);

        final ViewTimeLine viewTimeLine = convertToCommandType(command, inputLine, ViewTimeLine.class);
        assertTrue(inputLine.startsWith(viewTimeLine.user), "Invalid user " + viewTimeLine.user);
    }

    @ParameterizedTest()
    @DisplayName("Parse command for viewing Wall")
    @MethodSource("wallCommandForAllUsers")
    void viewWall(String inputLine) {
        final Command command = parseToCommand(inputLine);

        final ViewWall viewWall = convertToCommandType(command, inputLine, ViewWall.class);
        assertTrue(inputLine.startsWith(viewWall.user), "Invalid user " + viewWall.user);

    }

    private static List<String> commandLinePosts() {
        return TestScenarios.commandLinePosts();
    }

    private static List<String> commandLineFollows() {
        return TestScenarios.commandLineFollows();
    }

    private static List<String> wallCommandForAllUsers() {
        return TestScenarios.commandLineWall();
    }

    private static List<String> allUsers() {
        return TestScenarios.users();
    }

    private void validatePostCommand(String inputLine, Posting posting) {
        assertTrue(inputLine.startsWith(posting.actor), "Invalid actor " + posting.actor);
        assertFalse(posting.content.startsWith(ARROW), "Invalid content :" + posting.content);
        assertTrue(inputLine.endsWith(posting.content), "Invalid content :" + posting.content);
    }

    private <T extends Command> T convertToCommandType(Command commandToConvert, String origin, Class<T> expectedCommandClazz) {
        if (isWrongCommand(expectedCommandClazz, commandToConvert)) fail(buildWrongCommandMessage(origin, expectedCommandClazz, commandToConvert));
        return expectedCommandClazz.cast(commandToConvert);
    }

    private Command parseToCommand(String inputLine) {
        final Optional<Command> parseResult = CommandParser.parseCommand(inputLine);
        return parseResult.orElseGet(throwUnableToParseException(inputLine));
    }

    private boolean isWrongCommand(Class<? extends Command> commandClazz, Command command) {
        return !commandClazz.isInstance(command);
    }

    private String buildWrongCommandMessage(String inputLine, Class<? extends Command> commandClazz, Command command) {
        final String notCorrectCommandMessage = buildNotCorrectCommandMessage(inputLine, commandClazz);
        final String actualCommandMessage = buildActualCommandMessage(command);
        return notCorrectCommandMessage + actualCommandMessage;
    }

    private String buildActualCommandMessage(Command command) {
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