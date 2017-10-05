package be.solid.social.usecase;

public class Posting implements Command<Void> {
    public final String content;
    public final String actor;

    private Posting(Builder builder) {
        content = builder.content;
        actor = builder.actor;
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
