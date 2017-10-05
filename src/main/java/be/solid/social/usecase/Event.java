package be.solid.social.usecase;

import java.time.Instant;
import java.util.Objects;

public class Event {
    public final String user;
    public final String content;
    public final Instant time;

    private Event(Builder builder) {
        user = builder.user;
        content = builder.content;
        time = builder.time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return Objects.equals(user, event.user) && Objects.equals(content, event.content) && Objects.equals(time, event.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, content, time);
    }

    public static Builder newBuilder() {
        return new Builder();
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

        public Event build() {
            return new Event(this);
        }
    }
}
