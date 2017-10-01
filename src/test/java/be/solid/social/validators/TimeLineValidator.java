package be.solid.social.validators;

import be.solid.social.MessageData;
import be.solid.social.api.Message;

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


   public void validate(String subjectOfWallCommand, List<Message> resultFromWallCommand) {
        final List<Message> expectedTimeLine = buildExpectedMessages(subjectOfWallCommand);
        assertIterableEquals(expectedTimeLine, resultFromWallCommand, "The expected user time line did not match the retrieved timeline");
    }


    private List<Message> buildExpectedMessages(String subjectOfWallCommand) {
        return allPosts.stream()
                       .filter(x -> x.sender.equals(subjectOfWallCommand))
                       .map(expectedMessageFactory::buildExpectedMessage)
                       .collect(Collectors.toList());
    }
}
