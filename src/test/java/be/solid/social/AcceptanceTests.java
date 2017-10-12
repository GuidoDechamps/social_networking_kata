package be.solid.social;


import be.solid.social.domain.ReaderService;
import be.solid.social.impl.Messages;
import be.solid.social.impl.PrintMessagesDecorator;
import be.solid.social.usecase.*;
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

    private final SocialNetworkUseCases useCases;
    private final WallValidator wallValidator;
    private final MessageValidator singleMessageValidator;
    private final TimeLineValidator timeLineValidator;
    private final ManualClock clock;


    public AcceptanceTests(Messages messages, ManualClock clock) {
        final ReaderService readerService = PrintMessagesDecorator.decorate(messages, clock);
        this.clock = clock;
        this.useCases = new SocialNetworkUseCases(messages, readerService);

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
        post(input);

        final List<Event> messages = read(input.sender);

        singleMessageValidator.validateSingleMessage(messages, input);
    }

    @ParameterizedTest()
    @DisplayName("Read timeline")
    @MethodSource("senders")
    void readTimeLineFromSender(final String sender) {
        postAllMessages();

        final List<Event> messages = read(sender);

        timeLineValidator.validate(sender, messages);
    }

    @ParameterizedTest()
    @DisplayName("Wall with no subscriptions")
    @MethodSource("senders")
    void wallWithNoSubscriptions(final String sender) {
        //TODO What is all?
        postAllMessages();

        final List<Event> messages = wall(sender);

        timeLineValidator.validate(sender, messages);

    }

    @Test()
    @DisplayName("Wall with subscriptions for Charlie")
    void wallCharlieWithSubscriptions() {
        postAllMessages();
        follow(CHARLIE, ALICE);

        final List<Event> messages = wall(CHARLIE);

        wallValidator.validate(messages, ALICE, CHARLIE);

    }

    @Test()
    @DisplayName("Wall with subscriptions for Alice")
    void wallAliceWithSubscriptions() {
        postAllMessages();
        follow(ALICE, BOB);

        final List<Event> messages = wall(ALICE);

        wallValidator.validate(messages, BOB, ALICE);

    }

    @Test()
    @DisplayName("Wall with subscriptions for Bob")
    void wallBobWithSubscriptions() {
        postAllMessages();
        follow(BOB, CHARLIE);
        follow(BOB, ALICE);

        final List<Event> messages = wall(BOB);

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

    private void follow(String user, String target) {
        final Following following = createFollowingCommand(user, target);
        useCases.execute(following);
    }

    private Following createFollowingCommand(String user, String target) {
        return Following.newBuilder()
                        .withUser(user)
                        .withSubscriptionTopic(target)
                        .build();
    }

    private List<Event> read(String sender) {
        final ViewTimeLine viewTimeLine = createViewTimeLineCommand(sender);
        return useCases.execute(viewTimeLine);
    }

    private ViewTimeLine createViewTimeLineCommand(String sender) {
        return ViewTimeLine.newBuilder()
                           .withUser(sender)
                           .build();
    }

    private List<Event> wall(String sender) {
        final ViewWall viewWallCommand = createViewWallCommand(sender);
        return useCases.execute(viewWallCommand);
    }

    private ViewWall createViewWallCommand(String sender) {
        return ViewWall.newBuilder()
                       .withUser(sender)
                       .build();
    }

    private void post(MessageData input) {
        final Posting postCommand = createPostCommand(input);
        useCases.execute(postCommand);
    }

    private Posting createPostCommand(MessageData input) {
        return Posting.newBuilder()
                      .withActor(input.sender)
                      .withContent(input.message)
                      .build();
    }

    private void postAllMessages() {
        allPostsToBeMade().forEach(this::post);
    }


}
