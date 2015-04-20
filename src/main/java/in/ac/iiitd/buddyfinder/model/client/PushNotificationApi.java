package in.ac.iiitd.buddyfinder.model.client;

import in.ac.iiitd.buddyfinder.model.push.Data;
import in.ac.iiitd.buddyfinder.model.push.Device;
import retrofit.http.*;

import java.util.List;

/**
 * Created by Rishav Jain on 15-04-2015.
 */
public interface PushNotificationApi {

    String PUSH_NOTIFICATION_SERVICE_PATH = "/push";
    String REGISTERATION_PATH = PUSH_NOTIFICATION_SERVICE_PATH + "/register";

    @POST(REGISTERATION_PATH)
    boolean registerDevice(@Body Device device);

    @GET(REGISTERATION_PATH)
    List<Device> getRegistrationIds();

    @DELETE(REGISTERATION_PATH + "/{id}")
    boolean removeRegisteredDevices(@Path("id") String id);

    @POST(PUSH_NOTIFICATION_SERVICE_PATH)
    String pushNotificationToAll(@Body Data data);
}
