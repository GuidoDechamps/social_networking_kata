package be.solid.social;


import be.solid.social.api.Message;
import be.solid.social.api.PublishingService;
import be.solid.social.api.ReaderService;
import be.solid.social.impl.Messages;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(UseCaseParameterResolver.class)
@DisplayName("Social Kata Acceptance tests")
public class AcceptanceTests {

    private final PublishingService publishingService;
    private final ReaderService readerService;

    public AcceptanceTests(Messages messages) {
        this.publishingService = messages;
        this.readerService = messages;
    }

    @ParameterizedTest()
    @DisplayName("Publish single message")
    @MethodSource("allPostsToBeMade")
    void postMessage(final MessageInput input) {
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

        validateTimeLine(sender, messages);
    }


    private static List<MessageInput> allPostsToBeMade() {
        return TestScenarios.messagePosts();
    }

    private static List<String> senders() {
        return TestScenarios.senders();
    }

    private void validateTimeLine(String sender, List<Message> messages) {
        final List<Message> expectedTimeLine = buildExpectedMessages(sender);
        assertIterableEquals(expectedTimeLine, messages, "The expected time line did not match the retrieved timeline");
    }

    private void postAllMessages() {
        allPostsToBeMade().forEach(input -> publishingService.publish(input.sender, input.message));
    }

    private List<Message> buildExpectedMessages(String sender) {
        return buildExpectedMessages(sender, allPostsToBeMade());
    }

    private List<Message> buildExpectedMessages(String sender, List<MessageInput> messageInputs) {
        return messageInputs.stream()
                            .filter(x -> x.sender.equals(sender))
                            .map(MessageInput::toMessage)
                            .collect(Collectors.toList());
    }

    private void validateSingleMessage(List<Message> allReadMessages, MessageInput expectedMessage) {
        assertFalse(allReadMessages.isEmpty(), "No messages were present");
        assertAll("single status update message", () -> checkSingleMessage(allReadMessages), () -> checkMessagePresent(allReadMessages, expectedMessage));
    }

    private boolean checkMessagePresent(List<Message> allReadMessages, MessageInput expectedMessage) {
        return allReadMessages.contains(expectedMessage.toMessage());
    }

    private void checkSingleMessage(List<Message> allReadMessages) {
        assertTrue(allReadMessages.size() == 1, "Only one message was expected. Found " + allReadMessages);
    }

}
