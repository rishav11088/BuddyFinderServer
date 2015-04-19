package in.ac.iiitd.buddyfinder.model.object;

/**
 * Created by Rishav Jain on 18-04-2015.
 * ChatMessage
 */
public class ForumMessage {

    private String timeOfCreation;
    private String sender;
    private String message;

    public ForumMessage() {
    }

    public ForumMessage(String timeOfCreation, String sender, String message) {
        this.timeOfCreation = timeOfCreation;
        this.sender = sender;
        this.message = message;
    }

    public String getTimeOfCreation() {
        return timeOfCreation;
    }

    public void setTimeOfCreation(String timeOfCreation) {
        this.timeOfCreation = timeOfCreation;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    @Override
    public String toString() {
        return "ChatMessage{" +
                "timeOfCreation='" + timeOfCreation + '\'' +
                ", sender='" + sender + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
