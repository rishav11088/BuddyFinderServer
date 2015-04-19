package in.ac.iiitd.buddyfinder.model.client;

import in.ac.iiitd.buddyfinder.model.push.Data;
import retrofit.http.*;

/**
 * Created by Rishav Jain on 15-04-2015.
 */
public interface PushNotificationApi {

    String PUSH_NOTIFICATION_SERVICE_PATH = "/push";
    String REGISTERATION_PATH = PUSH_NOTIFICATION_SERVICE_PATH + "/register";

    @POST(REGISTERATION_PATH)
    boolean registerDevice(@Body String regId);

    @GET(REGISTERATION_PATH)
    String getRegistrationIds();

    @DELETE(REGISTERATION_PATH + "/{regId}")
    boolean removeRegisteredDevices(@Path("regId") String regId);

    @POST(PUSH_NOTIFICATION_SERVICE_PATH)
    String pushNotificationToAll(@Body Data data);
}
