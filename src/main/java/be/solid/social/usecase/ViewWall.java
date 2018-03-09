package be.solid.social.usecase;

import be.solid.social.domain.Message;
import be.solid.social.domain.ReaderService;

import java.util.List;

import static be.solid.social.usecase.EventMapper.map;
import static java.util.Objects.requireNonNull;

public class ViewWall extends ReadUseCase {
    private final ReaderService readerService;
    private final String user;

    private ViewWall(Builder builder) {
        super(builder);
        requireNonNull(builder.readerService);
        requireNonNull(builder.user);
        readerService = builder.readerService;
        user = builder.user;
    }

    @Override
    public void execute() {
        final List<Message> messages = readerService.readWall(user);
        final List<Event> events = map(messages);
        this.presenter.allEvents(events);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static final class Builder extends ReadUseCase.Builder<Builder> {
        private ReaderService readerService;
        private String user;

        private Builder() {
        }

        public Builder withUser(String val) {
            user = val;
            return this;
        }

        public Builder withReaderService(ReaderService readerService) {
            this.readerService = readerService;
            return this;
        }


        @Override
        public ViewWall build() {
            return new ViewWall(this);
        }

        @Override
        protected Builder self() {
            return this;
        }
    }
}
