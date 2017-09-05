package be.solid.social.console;

class Following implements Command {
    final String user;
    final String subscriptionTopic;

    private Following(Builder builder) {
        user = builder.user;
        subscriptionTopic = builder.subscriptionTopic;
    }

    public static Builder newBuilder() {
        return new Builder();
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
