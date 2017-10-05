package be.solid.social.validators;

import be.solid.social.MessageData;
import be.solid.social.domain.Message;
import be.solid.social.usecase.Event;

import java.util.List;
import java.util.stream.Collectors;

import static com.google.common.collect.Lists.newArrayList;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

public class WallValidator {
    private final ExpectedMessageFactory expectedMessageFactory;
    private final List<MessageData> allPosts;

    WallValidator(ExpectedMessageFactory expectedMessageFactory, List<MessageData> allPosts) {
        this.expectedMessageFactory = expectedMessageFactory;
        this.allPosts = allPosts;
    }

    public void validate(List<Event> messages, String... expectedSenders) {
        validateWall(messages, newArrayList(expectedSenders));
    }

    private void validateWall(List<Event> wallCommandResultToValidate, List<String> expectedSenders) {
        final List<Message> s = buildExpectedWallMessages(expectedSenders);
        assertIterableEquals(s, wallCommandResultToValidate, "The expected wall  did not match the retrieved wall");
    }

    private List<Message> buildExpectedWallMessages(List<String> expectedSenders) {
        return allPosts.stream()
                       .filter(x -> expectedSenders.contains(x.sender))
                       .map(expectedMessageFactory::buildExpectedMessage)
                       .collect(Collectors.toList());
    }


}
