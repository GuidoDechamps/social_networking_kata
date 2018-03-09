package be.solid.social;


import be.solid.social.domain.PublishingService;
import be.solid.social.domain.ReaderService;
import be.solid.social.impl.Messages;
import be.solid.social.impl.PrintMessagesDecorator;
import be.solid.social.usecase.*;
import be.solid.social.validators.MessageValidator;
import be.solid.social.validators.ValidatorFactory;
import be.solid.social.validators.WallResultValidator;
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

    private final ReaderService readerService;
    private final PublishingService publishingService;
    private final WallValidator wallValidator;
    private final MessageValidator singleMessageValidator;
    private final PresenterSpy presenterSpy;
    private final WallResultValidator wallResultValidator;
    private final ManualClock clock;


    public AcceptanceTests(Messages messages, ManualClock clock) {
        this.clock = clock;
        this.publishingService = messages;
        this.presenterSpy = new PresenterSpy();
        this.readerService = PrintMessagesDecorator.decorate(messages, clock);

        final ValidatorFactory validatorFactory = new ValidatorFactory(clock, presenterSpy);
        this.singleMessageValidator = validatorFactory.createSingleMessageValidator();
        this.wallResultValidator = validatorFactory.createTimeLineValidator(sequenceOfPosts());
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

        viewTimeLine(input.sender);

        singleMessageValidator.validateSingleMessage(input);
    }

    @ParameterizedTest()
    @DisplayName("Read timeline")
    @MethodSource("senders")
    void readTimeLineFromSender(final String sender) {
        postAllExampleMessages();

        viewTimeLine(sender);

        wallResultValidator.validate(sender);
    }

    @ParameterizedTest()
    @DisplayName("Wall with no subscriptions")
    @MethodSource("senders")
    void wallWithNoSubscriptions(final String sender) {
        postAllExampleMessages();

        wall(sender);

        wallResultValidator.validate(sender);

    }

    @Test()
    @DisplayName("Wall with subscriptions for Charlie")
    void wallCharlieWithSubscriptions() {
        postAllExampleMessages();
        follow(CHARLIE, ALICE);

        wall(CHARLIE);

        wallValidator.validate(ALICE, CHARLIE);

    }

    @Test()
    @DisplayName("Wall with subscriptions for Alice")
    void wallAliceWithSubscriptions() {
        postAllExampleMessages();
        follow(ALICE, BOB);

        wall(ALICE);

        wallValidator.validate(BOB, ALICE);

    }

    @Test()
    @DisplayName("Wall with subscriptions for Bob")
    void wallBobWithSubscriptions() {
        postAllExampleMessages();
        follow(BOB, CHARLIE);
        follow(BOB, ALICE);

        wall(BOB);

        wallValidator.validate(CHARLIE, BOB, ALICE);

    }

    private static List<MessageData> allPostsToBeMade() {
        return TestScenarios.sequenceOfPosts();
    }

    private static List<MessageData> individualPosts() {
        return TestScenarios.individualPosts();
    }

    private static List<String> senders() {
        return TestScenarios.userList();
    }

    private void follow(String user, String target) {
        final Following following = createFollowingCommand(user, target);
        following.execute();
    }

    private Following createFollowingCommand(String user, String target) {
        return Following.newBuilder()
                        .withPublishingService(publishingService)
                        .withUser(user)
                        .withSubscriptionTopic(target)
                        .build();
    }

    private void viewTimeLine(String sender) {
        final ViewTimeLine viewTimeLine = createViewTimeLineCommand(sender);
        viewTimeLine.execute();
    }

    private ViewTimeLine createViewTimeLineCommand(String sender) {
        return ViewTimeLine.newBuilder()
                           .withReaderService(readerService)
                           .withPresenter(presenterSpy)
                           .withUser(sender)
                           .build();
    }

    private void wall(String sender) {
        final ViewWall viewWallCommand = createViewWallCommand(sender);
        viewWallCommand.execute();
    }

    private ViewWall createViewWallCommand(String sender) {
        return ViewWall.newBuilder()
                       .withReaderService(readerService)
                       .withPresenter(presenterSpy)
                       .withUser(sender)
                       .build();
    }

    private void post(MessageData input) {
        final Posting postCommand = createPostCommand(input);
        postCommand.execute();
    }

    private Posting createPostCommand(MessageData input) {
        return Posting.newBuilder()
                      .withPublishingService(publishingService)
                      .withActor(input.sender)
                      .withContent(input.message)
                      .build();
    }

    private void postAllExampleMessages() {
        allPostsToBeMade().forEach(this::post);
    }


}
