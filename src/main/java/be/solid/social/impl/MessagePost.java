package be.solid.social.impl;

import java.time.LocalDateTime;

class MessagePost {

    public final String user;
    public final String content;
    public final LocalDateTime creationTime;

    private MessagePost(Builder builder) {
        user = builder.user;
        content = builder.content;
        creationTime = builder.creationTime;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static final class Builder {
        private String user;
        private String content;
        private LocalDateTime creationTime;

        private Builder() {
        }

        public Builder withUser(String val) {
            user = val;
            return this;
        }

        public Builder withContent(String val) {
            content = val;
            return this;
        }

        public Builder withCreationTime(LocalDateTime val) {
            creationTime = val;
            return this;
        }


        public MessagePost build() {
            return new MessagePost(this);
        }
    }
}
