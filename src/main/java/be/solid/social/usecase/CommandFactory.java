package be.solid.social.usecase;

import be.solid.social.domain.PublishingService;
import be.solid.social.domain.ReaderService;

//TODO rename usecaseFactory
public class CommandFactory {
    private final PublishingService publishingService;
    private final Presenter presenter;
    private final ReaderService readerService;

    public CommandFactory(PublishingService publishingService, Presenter presenter, ReaderService readerService) {
        this.publishingService = publishingService;
        this.presenter = presenter;
        this.readerService = readerService;
    }

    public Posting.Builder buildPostCommand() {
        return Posting.newBuilder().withPublishingService(publishingService).withPresenter(presenter);
    }
}
