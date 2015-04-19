package in.ac.iiitd.buddyfinder.model.client;

import in.ac.iiitd.buddyfinder.model.object.User;
import retrofit.client.Response;
import retrofit.http.*;
import retrofit.mime.TypedFile;

import java.util.List;

public interface UserServiceApi {
	
	String USER_SERVICE_PATH = "/user";

    String USER_ID_QUERY_PARAMETER = "id";
    String USER_ID_SEARCH_PATH = USER_SERVICE_PATH + "/search/findById";
    
    String USER_PHOTO_SERVICE_PATH = USER_SERVICE_PATH + "/photo";
    String PHOTO_UPLOAD_URI_QUERY = "uri";
    String PHOTO_UPLOAD_FILE_QUERY = "image";

    @POST(USER_SERVICE_PATH)
    User addUser(@Body User u);

    @GET(USER_SERVICE_PATH)
    List<User> getUsers();
    
    @GET(USER_ID_SEARCH_PATH)
    User findById(@Query(USER_ID_QUERY_PARAMETER) String id);
   
    @Multipart
    @POST(USER_PHOTO_SERVICE_PATH)
    boolean uploadImage(@Part(PHOTO_UPLOAD_URI_QUERY) String uri, @Part(PHOTO_UPLOAD_FILE_QUERY) TypedFile imageFile);
    
    @GET(USER_PHOTO_SERVICE_PATH + "/{id}")
    public Response getImage(@Path("id") String id);
}
