package be.solid.social;

import be.solid.social.api.Message;

import java.util.List;
import java.util.stream.Collectors;

import static com.google.common.collect.Lists.newArrayList;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

class WallValidator {

    private final List<MessageData> allPosts;

    WallValidator(List<MessageData> allPosts) {
        this.allPosts = allPosts;
    }

    void validate(List<Message> messages, String... expectedSenders) {
        validateWall(messages, newArrayList(expectedSenders));
    }

    private void validateWall(List<Message> messages, List<String> expectedSenders) {
        final List<String> messagesData = extractSenders(messages);
        final List<String> s = buildWallFromExpectedSenders(expectedSenders);
        assertIterableEquals(s, messagesData, "The expected time line did not match the retrieved timeline");
    }

    private List<String> buildWallFromExpectedSenders(List<String> expectedSenders) {
        return allPosts.stream()
                       .map(x -> x.sender)
                       .filter(expectedSenders::contains)
                       .collect(Collectors.toList());
    }

    private List<String> extractSenders(List<Message> messages) {
        return messages.stream()
                       .map(x -> x.user)
                       .collect(Collectors.toList());
    }


}
