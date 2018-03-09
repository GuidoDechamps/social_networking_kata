package be.solid.social.usecase;

import be.solid.social.domain.Message;

import java.util.List;

import static be.solid.social.usecase.EventMapper.map;
import static java.util.Objects.requireNonNull;

public class ViewWall extends ReadUseCase {
    private final String user;

    private ViewWall(Builder builder) {
        super(builder);
        requireNonNull(builder.user);
        user = builder.user;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    @Override
    public void execute() {
        final List<Message> messages = readerService.readWall(user);
        final List<Event> events = map(messages);
        this.presenter.allEvents(events);
    }

    public static final class Builder extends ReadUseCase.Builder<Builder> {
        private String user;

        private Builder() {
        }

        public Builder withUser(String val) {
            user = val;
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
