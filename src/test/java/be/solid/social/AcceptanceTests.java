package be.solid.social;


import be.solid.social.api.Message;
import be.solid.social.api.PublishingService;
import be.solid.social.api.ReaderService;
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


    @ParameterizedTest()
    @DisplayName("Publish single message")
    @MethodSource("messagePosts")
    void publishSingleStatusUpdate(final MessageInput input, final PublishingService publishingService, final ReaderService readerService) {
        publishingService.publish(input.sender, input.message);
        final List<Message> messages = readerService.read(input.sender);
        validateSingleMessage(messages, input);
    }

    @ParameterizedTest()
    @DisplayName("Read timeline")
    @MethodSource("senders")
    void readTimeLine(final String sender, final PublishingService publishingService, final ReaderService readerService) {
        messagePosts().forEach(input -> publishingService.publish(input.sender, input.message));
        final List<Message> messages = readerService.read(sender);
        final List<Message> expectedTimeLine = messagePosts().stream()
                                                             .filter(x -> x.sender.equals(sender))
                                                             .map(MessageInput::toMessage)
                                                             .collect(Collectors.toList());
        assertIterableEquals(expectedTimeLine, messages);
    }


    private static List<MessageInput> messagePosts() {
        return TestScenarios.messagePosts();
    }

    private static List<String> senders() {
        return TestScenarios.senders();
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
