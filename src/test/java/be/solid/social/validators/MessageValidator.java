package be.solid.social.validators;

import be.solid.social.MessageData;
import be.solid.social.domain.Message;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MessageValidator {
    private final ExpectedMessageFactory expectedMessageFactory;

    MessageValidator(ExpectedMessageFactory expectedMessageFactory) {
        this.expectedMessageFactory = expectedMessageFactory;
    }

    public void validate(MessageData originalMessageData, Message message) {
        final Message expectedMessage = expectedMessageFactory.buildExpectedMessage(originalMessageData);
        assertEquals(expectedMessage, message);
    }

    public void validateSingleMessage(List<Message> allReadMessages, MessageData originalInputData) {
        checkSingleMessage(allReadMessages);
        final Message message = allReadMessages.get(0);
        validate(originalInputData, message);

    }

    private void checkSingleMessage(List<Message> allReadMessages) {
        assertFalse(allReadMessages.isEmpty(), "No messages were present");
        assertTrue(allReadMessages.size() == 1, "Only one message was expected. Found " + allReadMessages);
    }
}
