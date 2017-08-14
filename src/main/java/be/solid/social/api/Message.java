package be.solid.social.api;

public class Message {
    public final String user;
    public final String content;

    private Message(String user, String content) {
        this.user = user;
        this.content = content;
    }

    public static Message create(String user, String content) {
        return new Message(user, content);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Message message = (Message) o;

        if (user != null ? !user.equals(message.user) : message.user != null) return false;
        return content != null ? content.equals(message.content) : message.content == null;
    }

    @Override
    public int hashCode() {
        int result = user != null ? user.hashCode() : 0;
        result = 31 * result + (content != null ? content.hashCode() : 0);
        return result;
    }
}
