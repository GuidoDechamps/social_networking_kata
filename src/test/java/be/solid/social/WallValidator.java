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
        final List<MessageData> messagesData = extractData(messages);
        final List<MessageData> expectedMessages = buildExpectedMessages(expectedSenders);
        assertIterableEquals(expectedMessages, messagesData, "The expected time line did not match the retrieved timeline");
    }

    private List<MessageData> extractData(List<Message> messages) {
        return messages.stream()
                       .map(this::mapToInput)
                       .collect(Collectors.toList());
    }

    private List<MessageData> buildExpectedMessages(List<String> senders) {
        return allPosts.stream()
                          .filter(x -> senders.contains(x.sender))
                          .collect(Collectors.toList());
    }


    private MessageData mapToInput(Message message) {
        return new MessageData(message.user, message.content);
    }
}
