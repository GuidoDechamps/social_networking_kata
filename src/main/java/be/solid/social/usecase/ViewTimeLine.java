package be.solid.social.usecase;

import be.solid.social.domain.Message;

import java.util.List;

import static be.solid.social.usecase.EventMapper.map;

public class ViewTimeLine extends ReadUseCase {

    private final String user;

    private ViewTimeLine(Builder builder) {
        super(builder);
        user = builder.user;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    @Override
    public void execute() {
        final List<Message> messages = readerService.read(user);
        final List<Event> events = map(messages);
        presenter.allEvents(events);
    }

    public static final class Builder extends ReadUseCase.Builder<ViewTimeLine.Builder> {
        private String user;


        public Builder withUser(String val) {
            user = val;
            return this;
        }

        @Override
        public ViewTimeLine build() {
            return new ViewTimeLine(this);
        }

        @Override
        protected ViewTimeLine.Builder self() {
            return this;
        }
    }
}
