package in.ac.iiitd.buddyfinder.controller;

import in.ac.iiitd.buddyfinder.model.client.UserServiceApi;
import in.ac.iiitd.buddyfinder.model.object.User;
import in.ac.iiitd.buddyfinder.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import retrofit.client.Response;
import retrofit.mime.TypedFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rishav Jain on 09-04-2015.
 * UserController
 */
@Controller
public class UserController implements UserServiceApi {

    private final static String PHOTOS_DIR = "photos\\";

    @Autowired
    UserRepository userRepository;

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public
    @ResponseBody
    String helloMessage() {
        return "Welcome to Buddy Finder Server !";
    }

    private String getPhotoUrl(User user) {
        return user.getId() + ".png";
    }

    @Override
    /*
    should contain id, firstName, lastName, phoneNumber, dateOfBirth
     */
    @RequestMapping(value = USER_SERVICE_PATH, method = RequestMethod.POST)
    public
    @ResponseBody
    User addUser(@RequestBody User user) {
        user.setProfilePhotoUrl(getPhotoUrl(user));
        user.setEventIds(new ArrayList<>());

        return userRepository.save(user);
    }

    @Override
    @RequestMapping(value = USER_SERVICE_PATH, method = RequestMethod.GET)
    public
    @ResponseBody
    List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    @RequestMapping(value = USER_ID_SEARCH_PATH, method = RequestMethod.GET)
    public @ResponseBody User findById(@RequestParam(USER_ID_QUERY_PARAMETER) String s) {
        return userRepository.findById(s);
    }

    @Override
    public boolean uploadImage(String s, TypedFile typedFile) {
        return false;
    }

    @RequestMapping(value = USER_PHOTO_SERVICE_PATH, method = RequestMethod.POST)
    public
    @ResponseBody
    boolean uploadImageImplementation(@RequestParam(PHOTO_UPLOAD_URI_QUERY) String uri, @RequestParam(PHOTO_UPLOAD_FILE_QUERY) MultipartFile file) {
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
                File outFile = new File(PHOTOS_DIR + uri);
                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(outFile));
                stream.write(bytes);
                stream.close();

                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    @Override
    public Response getImage(String s) {
        return null;
    }

    @RequestMapping(value = USER_PHOTO_SERVICE_PATH + "/{id}", method = RequestMethod.GET)
    public void getImageImplementation(@PathVariable("id") String id, HttpServletResponse response) {
        try {
            BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(new File(PHOTOS_DIR + id + ".png")));
            response.setContentType("image/png");
            response.setHeader("Content-Disposition", "attachment; filename=" + id + ".png");

            int size = bufferedInputStream.available();

            byte[] b = new byte[size];
            bufferedInputStream.read(b, 0, size);

            response.getOutputStream().write(b);
            bufferedInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
