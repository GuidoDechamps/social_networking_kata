package be.solid.social.usecase;

import be.solid.social.domain.PublishingService;
import be.solid.social.domain.ReaderService;

import static java.util.Objects.requireNonNull;

//TODO rename usecaseFactory
public class CommandFactory {
    private final PublishingService publishingService;
    private final Presenter presenter;
    private final ReaderService readerService;

    public CommandFactory(PublishingService publishingService, Presenter presenter, ReaderService readerService) {
        requireNonNull(publishingService);
        requireNonNull(presenter);
        requireNonNull(readerService);
        this.publishingService = publishingService;
        this.presenter = presenter;
        this.readerService = readerService;
    }

    public Posting.Builder postBuilder() {
        return Posting.newBuilder()
                      .withPublishingService(publishingService)
                      .withPresenter(presenter);
    }

    public ViewWall.Builder viewWallBuilder() {
        return ViewWall.newBuilder()
                       .withReaderService(readerService)
                       .withPresenter(presenter);
    }

    public Following.Builder followingBuilder() {
        return Following.newBuilder()
                        .withPublishingService(publishingService)
                        .withPresenter(presenter);
    }

    public ViewTimeLine.Builder viewTimeLineBuilder() {
        return ViewTimeLine.newBuilder()
                           .withPresenter(presenter)
                           .withReaderService(readerService);
    }
}
