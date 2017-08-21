package be.solid.social.console;

class ViewWall implements Command {
    final String user;

    private ViewWall(Builder builder) {
        user = builder.user;
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
