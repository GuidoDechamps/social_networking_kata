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
}
