package be.solid.social.validators;

import be.solid.social.MessageData;
import be.solid.social.domain.Message;
import be.solid.social.usecase.Event;
import be.solid.social.usecase.PresenterSpy;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MessageValidator {
    private final ExpectedMessageFactory expectedMessageFactory;
    private final PresenterSpy presenterSpy;

    MessageValidator(ExpectedMessageFactory expectedMessageFactory, PresenterSpy presenterSpy) {
        this.expectedMessageFactory = expectedMessageFactory;
        this.presenterSpy = presenterSpy;
    }

    public void validateSingleMessage(MessageData originalInputData) {
        final List<Event> allReadMessages = presenterSpy.getAllEvents();
        checkSingleMessage(allReadMessages);
        final Event message = allReadMessages.get(0);
        validate(originalInputData, message);

    }

    private void validate(MessageData originalMessageData, Event event) {
        final Message expectedMessage = expectedMessageFactory.buildExpectedMessage(originalMessageData);
        assertEquals(expectedMessage.user, event.user);
        assertEquals(expectedMessage.content, event.content);
        assertEquals(expectedMessage.user, event.user);
    }

    private void checkSingleMessage(List<Event> allReadMessages) {
        assertFalse(allReadMessages.isEmpty(), "No messages were present");
        assertTrue(allReadMessages.size() == 1, "Only one message was expected. Found " + allReadMessages);
    }
}
