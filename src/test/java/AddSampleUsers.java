import in.ac.iiitd.buddyfinder.model.client.UserServiceApi;
import in.ac.iiitd.buddyfinder.model.object.User;
import org.junit.Test;
import retrofit.RestAdapter;
import retrofit.mime.TypedFile;

import java.io.File;

/**
 * Created by Rishav Jain on 19-04-2015.
 */
public class AddSampleUsers {

    @Test
    public void addUsers() {
        UserServiceApi userServiceApi = new RestAdapter.Builder()
                .setEndpoint("http://localhost:8080")
                .build()
                .create(UserServiceApi.class);

        User user = new User();
        user.setId("0");
        user.setFirstName("Nishant");
        user.setLastName("Sharma");
        user.setPhoneNumber("0000000000");
        user.setDateOfBirth("00/00/00");

        user = userServiceApi.addUser(user);
        userServiceApi.uploadImage(user.getProfilePhotoUrl(), new TypedFile("image/png",
                new File("D:\\IIITD\\Semester VIII - Winter 2015\\(CSE5PCSMA) Programming Cloud Services for Mobile Applications\\Project\\nishant.jpg")));

        user = new User();
        user.setId("1");
        user.setFirstName("Mayank");
        user.setLastName("Garg");
        user.setPhoneNumber("1111111111");
        user.setDateOfBirth("11/11/11");

        user = userServiceApi.addUser(user);
        userServiceApi.uploadImage(user.getProfilePhotoUrl(), new TypedFile("image/png",
                new File("D:\\IIITD\\Semester VIII - Winter 2015\\(CSE5PCSMA) Programming Cloud Services for Mobile Applications\\Project\\mayank.jpg")));

        user = new User();
        user.setId("2");
        user.setFirstName("Sujit");
        user.setLastName("PB");
        user.setPhoneNumber("2222222222");
        user.setDateOfBirth("22/22/22");

        user = userServiceApi.addUser(user);
        userServiceApi.uploadImage(user.getProfilePhotoUrl(), new TypedFile("image/png",
                new File("D:\\IIITD\\Semester VIII - Winter 2015\\(CSE5PCSMA) Programming Cloud Services for Mobile Applications\\Project\\sujit.jpg")));
    }


    @Test
    public void getUsers() {
        UserServiceApi userServiceApi = new RestAdapter.Builder()
                .setEndpoint("http://localhost:8080")
                .build()
                .create(UserServiceApi.class);

        System.out.println(userServiceApi.getUsers());
    }
}
