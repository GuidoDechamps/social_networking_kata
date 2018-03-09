package be.solid.social.validators;

import be.solid.social.MessageData;
import be.solid.social.usecase.Event;
import be.solid.social.usecase.PresenterSpy;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;

public class WallResultValidator {

    private final List<MessageData> allPosts;
    private final ExpectedMessageFactory expectedMessageFactory;
    private final PresenterSpy presenterSpy;

    WallResultValidator(List<MessageData> allPosts, ExpectedMessageFactory expectedMessageFactory, PresenterSpy presenterSpy) {
        this.allPosts = allPosts;
        this.expectedMessageFactory = expectedMessageFactory;
        this.presenterSpy = presenterSpy;
    }


    public void validate(String subjectOfWallCommand) {
        final List<Event> expectedTimeLine = buildExpectedResultWallCommand(subjectOfWallCommand);
        assertIterableEquals(expectedTimeLine, presenterSpy.getAllEvents(), "The expected user time line did not match the retrieved timeline");
    }


    private List<Event> buildExpectedResultWallCommand(String subjectOfWallCommand) {
        return allPosts.stream()
                       .filter(x -> x.sender.equals(subjectOfWallCommand))
                       .map(expectedMessageFactory::buildExpectedEvent)
                       .collect(Collectors.toList());
    }
}
