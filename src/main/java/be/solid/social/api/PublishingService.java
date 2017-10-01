package be.solid.social.api;

public interface PublishingService {
    void post(String sender, String content);

    void subscribe(String subscriber, String topic);
}
