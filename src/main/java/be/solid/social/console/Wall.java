package be.solid.social.console;

class Wall {
    private final String user;

    private Wall(Builder builder) {
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

        public Wall build() {
            return new Wall(this);
        }
    }
}
