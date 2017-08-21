package be.solid.social.console;

class ViewTimeLine implements Command {
    final String user;

    private ViewTimeLine(Builder builder) {
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

        public ViewTimeLine build() {
            return new ViewTimeLine(this);
        }
    }
}
