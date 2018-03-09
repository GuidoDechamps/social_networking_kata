package be.solid.social.console;

import be.solid.social.TestScenarios;
import be.solid.social.domain.PublishingSpy;
import be.solid.social.domain.ReaderSpy;
import be.solid.social.usecase.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

@DisplayName("Console Command Parser Test")
class CommandParserTest {

    private PublishingSpy publishingSpy;
    private ReaderSpy readerSpy;
    private CommandParser parser;

    @BeforeEach
    void setUp() {
        publishingSpy = new PublishingSpy();
        readerSpy = new ReaderSpy();
        parser = new CommandParser(new CommandFactory(publishingSpy, null, readerSpy));
    }

    @ParameterizedTest()
    @DisplayName("Parse command for posting single message")
    @MethodSource("commandLinePosts")
    void postParsing(String inputLine) {
        final Usecase command = parse(inputLine);

        final Posting posting = convertToSpecificUseCase(command, inputLine, Posting.class);
        posting.execute();

        new PostingValidator(publishingSpy).validatePostCommand(inputLine);
    }

    @ParameterizedTest()
    @DisplayName("Parse command for userList following userList")
    @MethodSource("commandLineFollows")
    void followParsing(String inputLine) {
        final Usecase command = parse(inputLine);
//TODO this does two things, valiadat that it is a follow command, and convert t follow command, after which we test the follow command is build correctly
        final Following following = convertToSpecificUseCase(command, inputLine, Following.class);
        following.execute();


        new SubscriptionValidator(publishingSpy).validate(inputLine);

    }

    @ParameterizedTest()
    @DisplayName("Parse command for reading/viewing a User TimeLine")
    @MethodSource("allUsers")
    void viewTimeLine(String inputLine) {
        final Usecase command = parse(inputLine);

        final ViewTimeLine viewTimeLine = convertToSpecificUseCase(command, inputLine, ViewTimeLine.class);
        viewTimeLine.execute();

        final String read = readerSpy.getRequiredRead();
        assertTrue(inputLine.startsWith(read), "Invalid user " + read);
    }

    @ParameterizedTest()
    @DisplayName("Parse command for viewing Wall")
    @MethodSource("wallCommandForAllUsers")
    void viewWall(String inputLine) {
        final Usecase command = parse(inputLine);

        final ViewWall viewWall = convertToSpecificUseCase(command, inputLine, ViewWall.class);
        viewWall.execute();

        final String topic = readerSpy.getRequiredTopic();
        assertTrue(inputLine.startsWith(topic), "Invalid topic " + topic);

    }

    private static List<String> commandLinePosts() {
        return TestScenarios.postsGivenFromCommandLine();
    }

    private static List<String> commandLineFollows() {
        return TestScenarios.followsGivenFromCommandLine();
    }

    private static List<String> wallCommandForAllUsers() {
        return TestScenarios.wallCommandFromCommandLine();
    }

    private static List<String> allUsers() {
        return TestScenarios.userList();
    }


    private <T extends Usecase> T convertToSpecificUseCase(Usecase commandToConvert, String origin, Class<T> expectedCommandClazz) {
        if (isWrongCommand(expectedCommandClazz, commandToConvert)) fail(buildWrongCommandMessage(origin, expectedCommandClazz, commandToConvert));
        return expectedCommandClazz.cast(commandToConvert);
    }

    private Usecase parse(String inputLine) {
        final Optional<Usecase> parseResult = parser.parseCommand(inputLine);
        return parseResult.orElseGet(throwUnableToParseException(inputLine));
    }

    private boolean isWrongCommand(Class<? extends Usecase> commandClazz, Usecase command) {
        return !commandClazz.isInstance(command);
    }

    private String buildWrongCommandMessage(String inputLine, Class<? extends Usecase> commandClazz, Usecase command) {
        final String notCorrectCommandMessage = buildNotCorrectCommandMessage(inputLine, commandClazz);
        final String actualCommandMessage = buildActualCommandMessage(command);
        return notCorrectCommandMessage + actualCommandMessage;
    }

    private String buildActualCommandMessage(Usecase command) {
        return "It was a " + command.getClass()
                                    .getSimpleName() + " command.";
    }

    private String buildNotCorrectCommandMessage(String inputLine, Class<?> clazz) {
        return inputLine + " could not be parsed to a " + clazz.getSimpleName() + " command.";
    }

    private Supplier<Usecase> throwUnableToParseException(String inputLine) {
        return () -> {
            throw new RuntimeException("The command " + inputLine + " could not be parsed");
        };
    }
}