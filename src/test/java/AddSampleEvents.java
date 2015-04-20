import in.ac.iiitd.buddyfinder.model.Utils;
import in.ac.iiitd.buddyfinder.model.client.EventServiceApi;
import in.ac.iiitd.buddyfinder.model.object.Event;
import org.junit.Test;
import retrofit.RestAdapter;

import java.util.Date;

/**
 * Created by Rishav Jain on 19-04-2015.
 * AddSampleEvents
 */
public class AddSampleEvents {

    @Test
    public void addEvents() {

        EventServiceApi eventServiceApi = new RestAdapter.Builder()
                .setEndpoint("http://localhost:8080")
                .build()
                .create(EventServiceApi.class);

        int num = 1;
        Event event;

        for(String owner : new String[] { "0", "1", "2" }) {
            int i = 3;
            while(--i > 0) {
                event = new Event();
                event.setCategory(Event.CATEGORIES[0]);
                event.setDescription("DUMMY DESCRIPTION " + num);
                event.setLocation("LOCATION " + num);
                event.setLocationLatitude(0);
                event.setLocationLongitude(0);
                event.setOwnerId(owner);
                event.setTimeOfCreation(Utils.DATE_TIME_FORMAT.format(new Date()));
                event.setTimeOfEvent(Utils.DATE_TIME_FORMAT.format(new Date()));
                event.setTimeOfExpiry(Utils.DATE_TIME_FORMAT.format(new Date()));
                event.setTitle("TITLE " + num);
                eventServiceApi.addEvent(event);
                num++;

                event = new Event();
                event.setCategory(Event.CATEGORIES[1]);
                event.setDescription("DUMMY DESCRIPTION " + num);
                event.setLocation("LOCATION " + num);
                event.setLocationLatitude(0);
                event.setLocationLongitude(0);
                event.setOwnerId(owner);
                event.setTimeOfCreation(Utils.DATE_TIME_FORMAT.format(new Date()));
                event.setTimeOfEvent(Utils.DATE_TIME_FORMAT.format(new Date()));
                event.setTimeOfExpiry(Utils.DATE_TIME_FORMAT.format(new Date()));
                event.setTitle("TITLE " + num);
                eventServiceApi.addEvent(event);
                num++;

                event = new Event();
                event.setCategory(Event.CATEGORIES[2]);
                event.setDescription("DUMMY DESCRIPTION " + num);
                event.setLocation("LOCATION " + num);
                event.setLocationLatitude(0);
                event.setLocationLongitude(0);
                event.setOwnerId(owner);
                event.setTimeOfCreation(Utils.DATE_TIME_FORMAT.format(new Date()));
                event.setTimeOfEvent(Utils.DATE_TIME_FORMAT.format(new Date()));
                event.setTimeOfExpiry(Utils.DATE_TIME_FORMAT.format(new Date()));
                event.setTitle("TITLE " + num);
                eventServiceApi.addEvent(event);
                num++;
            }
        }
    }

    @Test
    public void getEvents() {
        EventServiceApi eventServiceApi = new RestAdapter.Builder()
                .setEndpoint("http://localhost:8080")
                .build()
                .create(EventServiceApi.class);

        System.out.println(eventServiceApi.getAllEvents());
    }
}
