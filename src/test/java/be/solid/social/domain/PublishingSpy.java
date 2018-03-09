package be.solid.social.domain;

import java.util.HashMap;
import java.util.Map;

public class PublishingSpy implements PublishingService {
    private final Map<String, String> posts = new HashMap<>();
    private final Map<String, String> subscriptions = new HashMap<>();

    @Override
    public void post(String sender, String content) {
        posts.put(sender, content);
    }

    @Override
    public void subscribe(String subscriber, String topic) {
        subscriptions.put(subscriber, topic);
    }

    public String getRequiredPoster() {
        ensureOnePost();
        return this.posts.keySet()
                         .iterator()
                         .next();
    }

    public String getRequiredContent() {
        ensureOnePost();
        return this.posts.values()
                         .iterator()
                         .next();
    }

    public String getRequiredSubscriber() {
        ensureOneSubscription();
        return this.subscriptions.keySet()
                                 .iterator()
                                 .next();
    }

    public String getRequiredTopic() {
        ensureOneSubscription();
        return this.subscriptions.values()
                                 .iterator()
                                 .next();
    }

    private void ensureOnePost() {
        if (posts.size() != 1) throw new ExactlyOnePostShouldHaveOccured(posts.size());
    }

    private void ensureOneSubscription() {
        if (subscriptions.size() != 1) throw new ExactlyOneSubscriptionShouldHaveOccured(subscriptions.size());
    }

    private class ExactlyOnePostShouldHaveOccured extends RuntimeException {
        public ExactlyOnePostShouldHaveOccured(int nrOfPosts) {
            super("Contained " + nrOfPosts + " posts");
        }
    }

    private class ExactlyOneSubscriptionShouldHaveOccured extends RuntimeException {
        public ExactlyOneSubscriptionShouldHaveOccured(int nrOfSubscriptions) {
            super("Contained " + nrOfSubscriptions + " subscriptions");
        }
    }
}
