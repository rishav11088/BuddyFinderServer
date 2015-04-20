import in.ac.iiitd.buddyfinder.model.Utils;
import in.ac.iiitd.buddyfinder.model.client.EventServiceApi;
import in.ac.iiitd.buddyfinder.model.object.ForumMessage;
import org.junit.Test;
import retrofit.RestAdapter;

import java.util.Date;

/**
 * Created by Rishav Jain on 20-04-2015.
 * sendForumMessage
 */
public class sendForumMessage {

    private EventServiceApi eventServiceApi;

    @Test
    public void sendMessage() {
        eventServiceApi = new RestAdapter.Builder()
                .setEndpoint("http://localhost:8080")
                .build()
                .create(EventServiceApi.class);

        String eventId = "5534eb94f7a967a01d20193f";
        String memberId = "1";
        String message = "main bhi aaya"; //"main bhi add ho gya kya?";

        eventServiceApi.addMemberToEvent(eventId,memberId);

        eventServiceApi.sendForumMessage(eventId, new ForumMessage(Utils.DATE_TIME_FORMAT.format(new Date()), memberId, message));

//        for(String sender : new String[] { "0", "1", "2" }) {
//            for(int i=0; i<5 ; i++) {
//                ForumMessage forumMessage = new ForumMessage("", sender, "DUMMY MESSAGE" + i);
//                eventServiceApi.sendForumMessage("5533e261f7a976e77758a73d", forumMessage);
//            }
//        }
    }

}
