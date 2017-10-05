package be.solid.social.usecase;

import be.solid.social.domain.Message;
import be.solid.social.domain.PublishingService;
import be.solid.social.domain.ReaderService;

import java.util.List;

import static be.solid.social.usecase.EventMapper.map;

public class SocialNetworkUseCases {

    private final PublishingService publishingService;
    private final ReaderService readerService;

    public SocialNetworkUseCases(PublishingService publishingService, ReaderService readerService) {
        this.publishingService = publishingService;
        this.readerService = readerService;
    }

    public void execute(Posting posting) {
        publishingService.post(posting.actor, posting.content);
    }

    public void execute(Following following) {
        publishingService.subscribe(following.user, following.subscriptionTopic);
    }

    public List<Event> execute(ViewTimeLine posting) {
        final List<Message> messages = readerService.read(posting.user);
        return map(messages);
    }

    public List<Event> execute(ViewWall posting) {
        final List<Message> messages = readerService.readWall(posting.user);
        return map(messages);
    }

}
