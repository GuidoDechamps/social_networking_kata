package be.solid.social.usecase;

public class Following extends Command {
    private final String user;
    private final String subscriptionTopic;

    private Following(Builder builder) {
        super(builder);
        user = builder.user;
        subscriptionTopic = builder.subscriptionTopic;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    @Override
    public void execute() {
        publishingService.subscribe(user, subscriptionTopic);
    }

    public final static class Builder extends Command.Builder<Following.Builder> {
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

        @Override
        protected Following.Builder self() {
            return this;
        }
    }
}
