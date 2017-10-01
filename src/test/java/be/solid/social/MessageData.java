package be.solid.social;

public class MessageData {
    public final int messageSequenceNumber;
    public final String sender;
    public final String message;


    MessageData(int messageSequenceNumber, String sender, String message) {
        this.messageSequenceNumber = messageSequenceNumber;
        this.sender = sender;
        this.message = message;
    }


    @Override
    public String toString() {
        return "MessageData{" + "sender='" + sender + '\'' + ", message='" + message + '\'' + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MessageData that = (MessageData) o;

        if (sender != null ? !sender.equals(that.sender) : that.sender != null) return false;
        return message != null ? message.equals(that.message) : that.message == null;
    }

    @Override
    public int hashCode() {
        int result = sender != null ? sender.hashCode() : 0;
        result = 31 * result + (message != null ? message.hashCode() : 0);
        return result;
    }
}
