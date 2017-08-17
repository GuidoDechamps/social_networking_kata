package be.solid.social.api;

import java.time.Instant;

public class Message {
    public final String user;
    public final String content;
    public final Instant time;

    private Message(Builder builder) {
        user = builder.user;
        content = builder.content;
        time = builder.time;
    }

    public static Builder newBuilder() {
        return new Builder();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Message message = (Message) o;

        if (user != null ? !user.equals(message.user) : message.user != null) return false;
        if (content != null ? !content.equals(message.content) : message.content != null) return false;
        return time != null ? time.equals(message.time) : message.time == null;
    }

    @Override
    public int hashCode() {
        int result = user != null ? user.hashCode() : 0;
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (time != null ? time.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Message{" + "user='" + user + '\'' + ", content='" + content + '\'' + ", time=" + time + '}';
    }

    public static final class Builder {
        private String user;
        private String content;
        private Instant time;

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

        public Builder withTime(Instant val) {
            time = val;
            return this;
        }

        public Message build() {
            return new Message(this);
        }
    }
}
