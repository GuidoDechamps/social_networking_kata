package be.solid.social.usecase;

import be.solid.social.domain.PublishingService;

import static java.util.Objects.nonNull;

public abstract class Command implements Usecase {
    protected final Presenter presenter;
    protected final PublishingService publishingService;

    protected Command(Builder<?> builder) {
        nonNull(builder.presenter);
        nonNull(builder.publishingService);
        this.presenter = builder.presenter;
        this.publishingService = builder.publishingService;
    }

    abstract static class Builder<BUILDER_TYPE extends Command.Builder<BUILDER_TYPE>> {
        private Presenter presenter;
        private PublishingService publishingService;

        public BUILDER_TYPE withPresenter(Presenter presenter) {
            this.presenter = presenter;
            return self();
        }

        public BUILDER_TYPE withPublishingService(PublishingService publishingService) {
            this.publishingService = publishingService;
            return self();
        }

        abstract Usecase build();

        protected abstract BUILDER_TYPE self();
    }

}
