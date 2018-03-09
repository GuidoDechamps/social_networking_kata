package be.solid.social.console;

import be.solid.social.domain.PublishingSpy;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class SubscriptionValidator {

    private final PublishingSpy publishingSpy;

    public SubscriptionValidator(PublishingSpy publishingSpy) {
        this.publishingSpy = publishingSpy;
    }

    public void validate(String inputLine) {
        final String subscriber = publishingSpy.getRequiredSubscriber();
        final String topic = publishingSpy.getRequiredTopic();
        assertTrue(inputLine.startsWith(subscriber), "Invalid user " + subscriber);
        assertTrue(inputLine.endsWith(topic), "Invalid user subscription :" + topic);
    }
}
