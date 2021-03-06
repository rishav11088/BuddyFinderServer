package in.ac.iiitd.buddyfinder.model.object;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rishav Jain on 18-04-2015.
 * Forum
 */
public class Forum {

    private String id;
    private List<ForumMessage> forumMessages;

    public Forum() {
        this.forumMessages = new ArrayList<ForumMessage>();
    }

    public Forum(String id) {
        this.id = id;
        this.forumMessages = new ArrayList<ForumMessage>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<ForumMessage> getForumMessages() {
        return forumMessages;
    }

    public void setForumMessages(List<ForumMessage> forumMessages) {
        this.forumMessages = forumMessages;
    }

    public void addForumMessage(ForumMessage forumMessage) {
        forumMessages.add(forumMessage);
    }

    @Override
    public String toString() {
        return "Forum{" +
                "id='" + id + '\'' +
                ", forumMessages=" + forumMessages +
                '}';
    }
}
