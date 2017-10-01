package be.solid.social;


import be.solid.social.domain.Message;
import be.solid.social.domain.PublishingService;
import be.solid.social.domain.ReaderService;
import be.solid.social.impl.Messages;
import be.solid.social.impl.PrintMessagesDecorator;
import be.solid.social.validators.MessageValidator;
import be.solid.social.validators.TimeLineValidator;
import be.solid.social.validators.ValidatorFactory;
import be.solid.social.validators.WallValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;

import static be.solid.social.TestScenarios.*;

@ExtendWith(UseCaseParameterResolver.class)
@DisplayName("Social Kata Acceptance tests")
public class AcceptanceTests {

    private final PublishingService publishingService;
    private final ReaderService readerService;
    private final WallValidator wallValidator;
    private final MessageValidator singleMessageValidator;
    private final TimeLineValidator timeLineValidator;
    private final SecondIncrementClock clock;


    public AcceptanceTests(Messages messages, SecondIncrementClock clock) {
        this.publishingService = messages;
        this.readerService = PrintMessagesDecorator.decorate(messages, clock);
        this.clock = clock;

        final ValidatorFactory validatorFactory = new ValidatorFactory(clock);
        this.singleMessageValidator = validatorFactory.createSingleMessageValidator();
        this.timeLineValidator = validatorFactory.createTimeLineValidator(sequenceOfPosts());
        this.wallValidator = validatorFactory.createWallValidator(sequenceOfPosts());
    }

    @BeforeEach
    void init() {
        clock.reset();
    }

    @ParameterizedTest()
    @DisplayName("Post single message")
    @MethodSource("individualPosts")
    void postMessage(final MessageData input) {
        publishingService.post(input.sender, input.message);

        final List<Message> messages = readerService.read(input.sender);

        singleMessageValidator.validateSingleMessage(messages, input);
    }

    @ParameterizedTest()
    @DisplayName("Read timeline")
    @MethodSource("senders")
    void readTimeLineFromSender(final String sender) {
        postAllMessages();

        final List<Message> messages = readerService.read(sender);

        timeLineValidator.validate(sender, messages);
    }

    @ParameterizedTest()
    @DisplayName("Wall with no subscriptions")
    @MethodSource("senders")
    void wallWithNoSubscriptions(final String sender) {
        postAllMessages();

        final List<Message> messages = readerService.readWall(sender);

        timeLineValidator.validate(sender, messages);

    }

    @Test()
    @DisplayName("Wall with subscriptions for Charlie")
    void wallCharlieWithSubscriptions() {
        postAllMessages();
        publishingService.subscribe(CHARLIE, ALICE);

        final List<Message> messages = readerService.readWall(CHARLIE);

        wallValidator.validate(messages, ALICE, CHARLIE);

    }

    @Test()
    @DisplayName("Wall with subscriptions for Alice")
    void wallAliceWithSubscriptions() {
        postAllMessages();
        publishingService.subscribe(ALICE, BOB);

        final List<Message> messages = readerService.readWall(ALICE);

        wallValidator.validate(messages, BOB, ALICE);

    }

    @Test()
    @DisplayName("Wall with subscriptions for Bob")
    void wallBobWithSubscriptions() {
        postAllMessages();
        publishingService.subscribe(BOB, CHARLIE);
        publishingService.subscribe(BOB, ALICE);

        final List<Message> messages = readerService.readWall(BOB);

        wallValidator.validate(messages, CHARLIE, BOB, ALICE);

    }

    private static List<MessageData> allPostsToBeMade() {
        return TestScenarios.sequenceOfPosts();
    }

    private static List<MessageData> individualPosts() {
        return TestScenarios.individualPosts();
    }

    private static List<String> senders() {
        return TestScenarios.users();
    }


    private void postAllMessages() {
        allPostsToBeMade().forEach(input -> publishingService.post(input.sender, input.message));
    }


}
