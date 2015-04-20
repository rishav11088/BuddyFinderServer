package in.ac.iiitd.buddyfinder.model.client;

import in.ac.iiitd.buddyfinder.model.object.Event;
import in.ac.iiitd.buddyfinder.model.object.ForumMessage;
import retrofit.http.*;

import java.util.List;

/**
 * Created by Rishav Jain on 19-04-2015.
 * EventServiceApi
 */
public interface EventServiceApi {

    String EVENT_SERVICE_PATH = "/event";

    String EVENT_ID_QUERY_PARAMETER = "id";
    String EVENT_ID_SEARCH_PATH = EVENT_SERVICE_PATH + "/search/findById";

    String EVENT_CATEGORY_QUERY_PARAMETER = "category";
    String EVENT_CATEGORY_SEARCH_PATH = EVENT_SERVICE_PATH + "/search/findByCategory";

    String EVENT_MEMBER_SERVICE_PATH = EVENT_SERVICE_PATH + "/member";
    String MEMBER_ID_QUERY_PARAMETER = "memberId";

    String EVENT_FORUM_SERVICE_PATH = EVENT_SERVICE_PATH + "/{" + EVENT_ID_QUERY_PARAMETER + "}/forum";

    @POST(EVENT_SERVICE_PATH)
    boolean addEvent(@Body Event e);

    @GET(EVENT_SERVICE_PATH)
    List<Event> getAllEvents();

    @DELETE(EVENT_SERVICE_PATH)
    boolean deleteEvent(@Query(EVENT_ID_QUERY_PARAMETER) String id);

    @GET(EVENT_ID_SEARCH_PATH)
    Event getEventById(@Query(EVENT_ID_QUERY_PARAMETER) String id);

    @GET(EVENT_CATEGORY_SEARCH_PATH)
    List<Event> getEventsByCategory(@Query(EVENT_CATEGORY_QUERY_PARAMETER) String category);

    @POST(EVENT_MEMBER_SERVICE_PATH)
    boolean addMemberToEvent(@Query(EVENT_ID_QUERY_PARAMETER) String eventId, @Query(MEMBER_ID_QUERY_PARAMETER) String memberId);

    @GET(EVENT_FORUM_SERVICE_PATH)
    List<ForumMessage> getForumMessages(@Path(EVENT_ID_QUERY_PARAMETER) String eventId);

    @POST(EVENT_FORUM_SERVICE_PATH)
    boolean sendForumMessage(@Path(EVENT_ID_QUERY_PARAMETER) String eventId, @Body ForumMessage forumMessage);
}
