package be.solid.social.usecase;

public class ViewWall implements Command {
    public final String user;

    private ViewWall(Builder builder) {
        user = builder.user;
    }

    @Override
    public void execute(SocialNetworkUseCases useCases) {
        useCases.execute(this);
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
