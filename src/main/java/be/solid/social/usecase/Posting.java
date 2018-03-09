package be.solid.social.usecase;

public class Posting extends Command {
    private final String content;
    private final String actor;

    private Posting(Posting.Builder builder) {
        super(builder);
        content = builder.content;
        actor = builder.actor;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    @Override
    public void execute() {
        publishingService.post(actor, content);
    }

    public static class Builder extends Command.Builder<Posting.Builder> {
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

        @Override
        protected Builder self() {
            return this;
        }
    }

}
