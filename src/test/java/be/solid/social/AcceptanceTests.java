package be.solid.social;


import be.solid.social.api.Message;
import be.solid.social.api.PublishingService;
import be.solid.social.api.ReaderService;
import be.solid.social.impl.Messages;
import be.solid.social.impl.PrintMessagesDecorator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Collectors;

import static be.solid.social.TestScenarios.*;
import static com.google.common.collect.Lists.newArrayList;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(UseCaseParameterResolver.class)
@DisplayName("Social Kata Acceptance tests")
public class AcceptanceTests {

    private final PublishingService publishingService;
    private final ReaderService readerService;


    public AcceptanceTests(Messages messages, SecondIncrementClock clock) {
        this.publishingService = messages;
        this.readerService = PrintMessagesDecorator.decorate(messages, clock);
    }

    @ParameterizedTest()
    @DisplayName("Publish single message")
    @MethodSource("allPostsToBeMade")
    void postMessage(final MessageData input) {
        publishingService.publish(input.sender, input.message);

        final List<Message> messages = readerService.read(input.sender);

        validateSingleMessage(messages, input);
    }

    @ParameterizedTest()
    @DisplayName("Read timeline")
    @MethodSource("senders")
    void readTimeLineFromSender(final String sender) {
        postAllMessages();

        final List<Message> messages = readerService.read(sender);

        validateUserTimeLine(sender, messages);
    }

    @ParameterizedTest()
    @DisplayName("Wall with no subscriptions")
    @MethodSource("senders")
    void wallWithNoSubscriptions(final String sender) {
        postAllMessages();

        final List<Message> messages = readerService.readWall(sender);

        validateUserTimeLine(sender, messages);

    }

    @Test()
    @DisplayName("Wall with subscriptions for Charlie")
    void wallCharlieWithSubscriptions() {
        postAllMessages();
        publishingService.subscribe(CHARLIE, ALICE);

        final List<Message> messages = readerService.readWall(CHARLIE);

        validateWall(messages, newArrayList(ALICE, CHARLIE));

    }

    @Test()
    @DisplayName("Wall with subscriptions for Alice")
    void wallAliceWithSubscriptions() {
        postAllMessages();
        publishingService.subscribe(ALICE, BOB);

        final List<Message> messages = readerService.readWall(ALICE);

        validateWall(messages, newArrayList(BOB, ALICE));

    }

    private static List<MessageData> allPostsToBeMade() {
        return TestScenarios.messagePosts();
    }

    private static List<String> senders() {
        return TestScenarios.senders();
    }


    private void validateWall(List<Message> messages, List<String> senders) {
        final List<MessageData> messagesData = extractData(messages);
        final List<MessageData> expectedMessages = buildExpectedMessages(senders, allPostsToBeMade());
        assertIterableEquals(expectedMessages, messagesData, "The expected time line did not match the retrieved timeline");
    }

    private List<MessageData> extractData(List<Message> messages) {
        return messages.stream()
                       .map(this::mapToInput)
                       .collect(Collectors.toList());
    }

    private void validateUserTimeLine(String sender, List<Message> messages) {
        final List<MessageData> messagesData = extractData(messages);
        final List<MessageData> expectedTimeLine = buildExpectedMessages(sender);
        assertIterableEquals(expectedTimeLine, messagesData, "The expected time line did not match the retrieved timeline");
    }

    private void postAllMessages() {
        allPostsToBeMade().forEach(input -> publishingService.publish(input.sender, input.message));
    }

    private List<MessageData> buildExpectedMessages(String sender) {
        return buildExpectedMessages(sender, allPostsToBeMade());
    }

    //TODO remove
    private List<MessageData> buildExpectedMessages(String sender, List<MessageData> messageData) {
        return messageData.stream()
                          .filter(x -> x.sender.equals(sender))
                          .collect(Collectors.toList());
    }

    private List<MessageData> buildExpectedMessages(List<String> senders, List<MessageData> messageData) {
        return messageData.stream()
                          .filter(x -> senders.contains(x.sender))
                          .collect(Collectors.toList());
    }

    private void validateSingleMessage(List<Message> allReadMessages, MessageData expectedMessage) {
        assertFalse(allReadMessages.isEmpty(), "No messages were present");
        assertAll("single status update message", () -> checkSingleMessage(allReadMessages), () -> checkMessagePresent(allReadMessages, expectedMessage));
    }

    private boolean checkMessagePresent(List<Message> allReadMessages, MessageData expectedMessage) {
        final List<MessageData> messagesData = extractData(allReadMessages);
        return messagesData.contains(expectedMessage);
    }

    private void checkSingleMessage(List<Message> allReadMessages) {
        assertTrue(allReadMessages.size() == 1, "Only one message was expected. Found " + allReadMessages);
    }

    private MessageData mapToInput(Message message) {
        return new MessageData(message.user, message.content);
    }

}
