package be.solid.social;


import be.solid.social.api.Message;
import be.solid.social.api.PublishingService;
import be.solid.social.api.ReaderService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;

import static be.solid.social.api.Message.create;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(UseCaseParameterResolver.class)
@DisplayName("Social Kata Acceptance tests")
public class AcceptanceTests {

    private static final String I_LOVE_THE_WEATHER_TODAY = "I love the weather today";
    private static final String ALICE = "Alice";

    @Test
    @DisplayName("Publish single message")
    void publishSingleStatusUpdate(final PublishingService publishingService, final ReaderService readerService) {
        publishingService.publish(ALICE, I_LOVE_THE_WEATHER_TODAY);
        final List<Message> messages = readerService.read(ALICE);

        validateSingleMessage(messages, create(ALICE, I_LOVE_THE_WEATHER_TODAY));
    }

    private void validateSingleMessage(List<Message> allReadMessages, Message expectedMessage) {
        assertFalse(allReadMessages.isEmpty(), "No messages were present");
        assertAll("single status update message",
                () -> checkSingleMessage(allReadMessages),
                () -> checkMessagePresent(allReadMessages, expectedMessage));
    }

    private boolean checkMessagePresent(List<Message> allReadMessages, Message expectedMessage) {
        return allReadMessages.contains(expectedMessage);
    }

    private void checkSingleMessage(List<Message> allReadMessages) {
        assertTrue(allReadMessages.size() == 1, "Only one message was expected");
    }
}
