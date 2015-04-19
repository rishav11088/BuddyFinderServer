import in.ac.iiitd.buddyfinder.model.client.PushNotificationApi;
import in.ac.iiitd.buddyfinder.model.push.Data;
import org.junit.Test;
import retrofit.RestAdapter;

/**
 * Created by Rishav Jain on 17-04-2015.
 * NotificationTest
 */
public class NotificationTest {

    @Test
    public void sendNotification() {

        PushNotificationApi pushNotificationApi = new RestAdapter.Builder()
                .setEndpoint("http://localhost:8080")
                .build()
                .create(PushNotificationApi.class);

        System.out.println(pushNotificationApi.pushNotificationToAll(new Data("type1", "title1", "message1")));
        System.out.println(pushNotificationApi.pushNotificationToAll(new Data("type2", "title2", "message2")));
        System.out.println(pushNotificationApi.pushNotificationToAll(new Data("type3", "title3", "message3")));
    }
}
