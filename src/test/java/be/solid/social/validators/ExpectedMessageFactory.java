package be.solid.social.validators;

import be.solid.social.MessageData;
import be.solid.social.TestClock;
import be.solid.social.api.Message;

class ExpectedMessageFactory {

    private final TestClock clock;

    ExpectedMessageFactory(TestClock clock) {
        this.clock = clock;
    }

    Message buildExpectedMessage(MessageData message) {
        return Message.newBuilder()
                      .withUser(message.sender)
                      .withContent(message.message)
                      .withTime(clock.getPostTime(message.messageSequenceNumber - 1))
                      .build();
    }
}
