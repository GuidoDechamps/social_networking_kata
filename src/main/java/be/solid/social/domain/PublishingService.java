package be.solid.social.domain;

public interface PublishingService {
    //TODO do not use string. do a command?
    void post(String sender, String content);

    void subscribe(String subscriber, String topic);
}
