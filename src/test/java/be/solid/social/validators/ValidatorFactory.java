package be.solid.social.validators;

import be.solid.social.ManualClock;
import be.solid.social.MessageData;
import be.solid.social.usecase.PresenterSpy;

import java.util.List;

public class ValidatorFactory {
    private final ExpectedMessageFactory expectedMessageFactory;
    private final PresenterSpy presenterSpy;


    public ValidatorFactory(ManualClock clock, PresenterSpy presenterSpy) {
        this.expectedMessageFactory = new ExpectedMessageFactory(clock);
        this.presenterSpy = presenterSpy;
    }


    public WallResultValidator createTimeLineValidator(List<MessageData> allMessages) {
        return new WallResultValidator(allMessages, expectedMessageFactory, presenterSpy);
    }

    public WallValidator createWallValidator(List<MessageData> allMessages) {
        return new WallValidator(expectedMessageFactory, allMessages, presenterSpy);
    }

    public MessageValidator createSingleMessageValidator() {
        return new MessageValidator(expectedMessageFactory, presenterSpy);
    }
}
