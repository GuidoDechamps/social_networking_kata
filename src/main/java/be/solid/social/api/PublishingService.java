package be.solid.social.api;

public interface PublishingService {
    void publish(String sender, String content);

    void subscribe(String subscriber, String topic);
}
