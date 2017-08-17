package be.solid.social.console;

class Reading implements Command{
    private final String user;

    private Reading(Builder builder) {
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

        public Reading build() {
            return new Reading(this);
        }
    }
}
