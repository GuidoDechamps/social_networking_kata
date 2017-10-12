package be.solid.social.validators;

import be.solid.social.MessageData;
import be.solid.social.ManualClock;

import java.util.List;

public class ValidatorFactory {
    private final ExpectedMessageFactory expectedMessageFactory;


    public ValidatorFactory(ManualClock clock) {
        this.expectedMessageFactory = new ExpectedMessageFactory(clock);
    }


    public TimeLineValidator createTimeLineValidator(List<MessageData> allMessages) {
        return new TimeLineValidator(allMessages, expectedMessageFactory);
    }

    public WallValidator createWallValidator(List<MessageData> allMessages) {
        return new WallValidator(expectedMessageFactory, allMessages);
    }

    public MessageValidator createSingleMessageValidator() {
        return new MessageValidator(expectedMessageFactory);
    }
}
