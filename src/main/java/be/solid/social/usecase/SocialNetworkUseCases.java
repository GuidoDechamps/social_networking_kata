package be.solid.social.usecase;

import be.solid.social.domain.PublishingService;
import be.solid.social.domain.ReaderService;

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

    public void execute(ViewTimeLine posting) {
        readerService.read(posting.user);
    }

    public void execute(ViewWall posting) {
        readerService.readWall(posting.user);
    }
}
