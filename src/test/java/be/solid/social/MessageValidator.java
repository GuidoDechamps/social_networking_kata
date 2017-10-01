package be.solid.social;

import be.solid.social.api.Message;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MessageValidator {
    private final MessageFactory messageFactory;

    MessageValidator(MessageFactory messageFactory) {
        this.messageFactory = messageFactory;
    }

    public void validate(MessageData originalMessageData, Message message) {
        final Message expectedMessage = messageFactory.buildExpectedMessage(originalMessageData);
        assertEquals(originalMessageData.sender, message.user);
        assertEquals(originalMessageData.message, message.content);
        assertEquals(expectedMessage.time, message.time);
    }

    void validateSingleMessage(List<Message> allReadMessages, MessageData originalInputData) {
        checkSingleMessage(allReadMessages);
        final Message message = allReadMessages.get(0);
        validate(originalInputData, message);

    }

    private void checkSingleMessage(List<Message> allReadMessages) {
        assertFalse(allReadMessages.isEmpty(), "No messages were present");
        assertTrue(allReadMessages.size() == 1, "Only one message was expected. Found " + allReadMessages);
    }
}
