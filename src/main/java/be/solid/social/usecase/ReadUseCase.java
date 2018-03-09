package be.solid.social.usecase;

import be.solid.social.domain.ReaderService;

import static java.util.Objects.requireNonNull;

abstract class ReadUseCase implements Usecase {
    protected final Presenter presenter;
    protected final ReaderService readerService;

    protected ReadUseCase(Builder<?> builder) {
        requireNonNull(builder.presenter);
        requireNonNull(builder.readerService);
        this.presenter = builder.presenter;
        this.readerService = builder.readerService;
    }

    abstract static class Builder<T extends Builder<T>> {
        private Presenter presenter;
        private ReaderService readerService;

        public T withPresenter(Presenter presenter) {
            this.presenter = presenter;
            return self();
        }

        public T withReaderService(ReaderService readerService) {
            this.readerService = readerService;
            return self();
        }

        abstract Usecase build();

        protected abstract T self();
    }
}
