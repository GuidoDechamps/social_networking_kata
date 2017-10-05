package be.solid.social.validators;

import be.solid.social.MessageData;
import be.solid.social.usecase.Event;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;

public class TimeLineValidator {

    private final List<MessageData> allPosts;
    private final ExpectedMessageFactory expectedMessageFactory;

    TimeLineValidator(List<MessageData> allPosts, ExpectedMessageFactory expectedMessageFactory) {
        this.allPosts = allPosts;
        this.expectedMessageFactory = expectedMessageFactory;
    }


   public void validate(String subjectOfWallCommand, List<Event> resultFromWallCommand) {
        final List<Event> expectedTimeLine = buildExpectedMessages(subjectOfWallCommand);
        assertIterableEquals(expectedTimeLine, resultFromWallCommand, "The expected user time line did not match the retrieved timeline");
    }


    private List<Event> buildExpectedMessages(String subjectOfWallCommand) {
        return allPosts.stream()
                       .filter(x -> x.sender.equals(subjectOfWallCommand))
                       .map(expectedMessageFactory::buildExpectedEvent)
                       .collect(Collectors.toList());
    }
}
