import in.ac.iiitd.buddyfinder.model.client.EventServiceApi;
import in.ac.iiitd.buddyfinder.model.object.Event;
import org.junit.Test;
import retrofit.RestAdapter;

/**
 * Created by Rishav Jain on 19-04-2015.
 */
public class EventsTest {

    @Test
    public void checkGetEventsByCategory() {

        EventServiceApi eventServiceApi = new RestAdapter.Builder()
                .setEndpoint("http://localhost:8080")
                .build()
                .create(EventServiceApi.class);

        System.out.println(eventServiceApi.getEventsByCategory(Event.CATEGORIES[0]));
        System.out.println(eventServiceApi.getEventsByCategory(Event.CATEGORIES[1]));
        System.out.println(eventServiceApi.getEventsByCategory(Event.CATEGORIES[2]));
    }
}
