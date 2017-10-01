package be.solid.social.domain;

public interface PublishingService {
    void post(String sender, String content);

    void subscribe(String subscriber, String topic);
}
