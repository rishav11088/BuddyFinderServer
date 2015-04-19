package in.ac.iiitd.buddyfinder.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import in.ac.iiitd.buddyfinder.model.client.PushNotificationApi;
import in.ac.iiitd.buddyfinder.model.push.Content;
import in.ac.iiitd.buddyfinder.model.push.Data;
import in.ac.iiitd.buddyfinder.model.push.Device;
import in.ac.iiitd.buddyfinder.repository.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Rishav Jain on 16-04-2015.
 * PushNotificationController
 */
@Controller
public class PushNotificationController /*implements PushNotificationApi*/ {

    public final static String GCM_ENDPOINT = "https://android.googleapis.com/gcm/send";
    private final static String API_KEY = "AIzaSyDbLnbW-jq-VroIT8CFf5RJMKsRDVTvZw0";

    @Autowired
    DeviceRepository RegIds;

    @RequestMapping(value = PushNotificationApi.REGISTERATION_PATH, method = RequestMethod.POST)
    public @ResponseBody ResponseEntity<Boolean> registerDevice(@RequestBody String regId) {
        Device device = new Device(regId);
        System.out.println("notification registation request - " + regId);
        if (!RegIds.findAll().contains(device)) {
            RegIds.save(device);
            System.out.println("registered - " + regId);
            return new ResponseEntity<Boolean>(true, HttpStatus.OK);
        } else {
            System.out.println("already registered - " + regId);
            return new ResponseEntity<Boolean>(false, HttpStatus.FOUND);
        }
    }

    @RequestMapping(value = PushNotificationApi.REGISTERATION_PATH, method = RequestMethod.GET)
    public @ResponseBody String getRegistrationIds() {
        return RegIds.findAll().toString();
    }

    @RequestMapping(value = PushNotificationApi.REGISTERATION_PATH, method = RequestMethod.DELETE)
    public @ResponseBody ResponseEntity<Boolean> removeRegisteredDevices(@PathVariable("regId") String regId) {
        Device device = new Device(regId);
        if (regId == null) {
            RegIds.deleteAll();
            return new ResponseEntity<Boolean>(true, HttpStatus.OK);
        } else if (RegIds.findAll().contains(device)) {
            RegIds.delete(device);
            return new ResponseEntity<Boolean>(true, HttpStatus.OK);
        } else {
            return new ResponseEntity<Boolean>(false, HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = PushNotificationApi.PUSH_NOTIFICATION_SERVICE_PATH, method = RequestMethod.POST)
    public @ResponseBody String pushNotificationtoAll(@RequestBody Data data) {
        try {
            List<String> listStrings = RegIds.findAll().stream().map(device -> device.getRegistrationId().replaceAll("\"", "")).collect(Collectors.toList());
            listStrings.add("1");
            listStrings.add("2");
            System.out.println("RegIds : " + listStrings);
//            String apiKey = API_KEY;
//            String deviceID=RegIds.findAll().get(0).getRegistrationId().replaceAll("\"", "");//Device Id
            Content content = new Content(listStrings);
            content.setData(data);
//            content.createData("Title", "Notification Message");
            URL url = new URL("https://android.googleapis.com/gcm/send");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Authorization", "key="+API_KEY);
            conn.setDoOutput(true);
            ObjectMapper mapper = new ObjectMapper();
            DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
            System.out.println(mapper.writeValueAsString(content));
            mapper.writeValue(wr, content);
            wr.flush();
            wr.close();

            int responseCode = conn.getResponseCode();
            System.out.println("\nSending 'POST' request to URL : " + url);
            System.out.println("Response Code : " + responseCode);

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            System.out.println(response.toString());

//            GcmResponse gcmResponse = mapper.readValue(response, GcmResponse.class);
            return new ObjectMapper().writeValueAsString(response);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return "{ }";
    }

//        GcmService gcmService = new RestAdapter.Builder()
//                .setClient(new ApacheClient(new EasyHttpClient()))
//                .setEndpoint(GCM_ENDPOINT)
//                .setLogLevel(RestAdapter.LogLevel.FULL)
//                .build()
//                .create(GcmService.class);
//
//        List<String> listStrings = new ArrayList<>();
//        for(Device device : RegIds.findAll()) {
//            listStrings.add(device.getRegistrationId());
//        }
//
//        GcmNotification gcmNotification = new GcmNotification(data, listStrings);
//
//        System.out.println(gcmNotification.getData().toJSON());
//        System.out.println(gcmNotification.toJSON());
//
//        try {
//            GcmResponse gcmResponse = gcmService.pushToGcm(gcmNotification.toJSON());
//            return gcmResponse.toString();
//        } catch (RetrofitError e) {
//            e.printStackTrace();
//        }
//
//        return "";
//}
}
