package be.solid.social.validators;

import be.solid.social.MessageData;
import be.solid.social.domain.Message;
import be.solid.social.usecase.Event;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MessageValidator {
    private final ExpectedMessageFactory expectedMessageFactory;

    MessageValidator(ExpectedMessageFactory expectedMessageFactory) {
        this.expectedMessageFactory = expectedMessageFactory;
    }

    public void validate(MessageData originalMessageData, Event event) {
        final Message expectedMessage = expectedMessageFactory.buildExpectedMessage(originalMessageData);
        assertEquals(expectedMessage.user, event.user);
        assertEquals(expectedMessage.content, event.content);
        assertEquals(expectedMessage.user, event.user);
    }

    public void validateSingleMessage(List<Event> allReadMessages, MessageData originalInputData) {
        checkSingleMessage(allReadMessages);
        final Event message = allReadMessages.get(0);
        validate(originalInputData, message);

    }

    private void checkSingleMessage(List<Event> allReadMessages) {
        assertFalse(allReadMessages.isEmpty(), "No messages were present");
        assertTrue(allReadMessages.size() == 1, "Only one message was expected. Found " + allReadMessages);
    }
}
