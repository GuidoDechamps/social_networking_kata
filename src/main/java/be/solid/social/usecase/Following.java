package be.solid.social.usecase;

public class Following implements Command<Void> {
    public final String user;
    public final String subscriptionTopic;

    private Following(Builder builder) {
        user = builder.user;
        subscriptionTopic = builder.subscriptionTopic;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    @Override
    public Void execute(SocialNetworkUseCases useCases) {
        useCases.execute(this);
        return null;
    }

    public static final class Builder {
        private String user;
        private String subscriptionTopic;

        private Builder() {
        }

        public Builder withUser(String val) {
            user = val;
            return this;
        }

        public Builder withSubscriptionTopic(String val) {
            subscriptionTopic = val;
            return this;
        }

        public Following build() {
            return new Following(this);
        }
    }
}
