package be.solid.social.validators;

import be.solid.social.MessageData;
import be.solid.social.usecase.Event;
import be.solid.social.usecase.PresenterSpy;

import java.util.List;
import java.util.stream.Collectors;

import static com.google.common.collect.Lists.newArrayList;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

public class WallValidator {
    private final ExpectedMessageFactory expectedMessageFactory;
    private final List<MessageData> allPosts;
    private final PresenterSpy presenterSpy;

    WallValidator(ExpectedMessageFactory expectedMessageFactory, List<MessageData> allPosts, PresenterSpy presenterSpy) {
        this.expectedMessageFactory = expectedMessageFactory;
        this.allPosts = allPosts;
        this.presenterSpy = presenterSpy;
    }

    public void validate(String... expectedSenders) {
        validateWall(newArrayList(expectedSenders));
    }

    private void validateWall(List<String> expectedSenders) {
        final List<Event> events = buildExpectedWallMessages(expectedSenders);
        assertIterableEquals(events, presenterSpy.getAllEvents(), "The expected wall  did not match the retrieved wall");
    }

    private List<Event> buildExpectedWallMessages(List<String> expectedSenders) {
        return allPosts.stream()
                       .filter(x -> expectedSenders.contains(x.sender))
                       .map(expectedMessageFactory::buildExpectedEvent)
                       .collect(Collectors.toList());
    }


}
