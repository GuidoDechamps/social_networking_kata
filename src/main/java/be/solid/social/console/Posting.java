package be.solid.social.console;

class Posting {
    private final String content;
    private final String actor;

    private Posting(Builder builder) {
        content = builder.content;
        actor = builder.actor;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static final class Builder {
        private String content;
        private String actor;

        private Builder() {
        }

        public Builder withContent(String val) {
            content = val;
            return this;
        }

        public Builder withActor(String val) {
            actor = val;
            return this;
        }

        public Posting build() {
            return new Posting(this);
        }
    }
}
