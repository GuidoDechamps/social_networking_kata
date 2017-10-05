package be.solid.social.usecase;

import java.util.List;

public class ViewTimeLine implements Command<List<Event>> {
    public final String user;

    private ViewTimeLine(Builder builder) {
        user = builder.user;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    @Override
    public List<Event> execute(SocialNetworkUseCases useCases) {
        return useCases.execute(this);
    }

    public static final class Builder {
        private String user;

        private Builder() {
        }

        public Builder withUser(String val) {
            user = val;
            return this;
        }

        public ViewTimeLine build() {
            return new ViewTimeLine(this);
        }
    }
}
