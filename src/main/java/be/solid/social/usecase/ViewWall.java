package be.solid.social.usecase;

import java.util.List;

public class ViewWall implements Command<List<Event>> {
    public final String user;

    private ViewWall(Builder builder) {
        user = builder.user;
    }

    @Override
    public List<Event> execute(SocialNetworkUseCases useCases) {
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

        public ViewWall build() {
            return new ViewWall(this);
        }
    }
}
