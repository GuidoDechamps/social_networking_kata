package be.solid.social.usecase;

public class ViewTimeLine implements Command<Event> {
    public final String user;

    private ViewTimeLine(Builder builder) {
        user = builder.user;
    }

    @Override
    public Event execute(SocialNetworkUseCases useCases) {
        useCases.execute(this);
        return null;
    }

    public static Builder newBuilder() {
        return new Builder();
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
