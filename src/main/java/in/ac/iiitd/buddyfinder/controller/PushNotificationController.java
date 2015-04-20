package in.ac.iiitd.buddyfinder.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import in.ac.iiitd.buddyfinder.Application;
import in.ac.iiitd.buddyfinder.model.client.PushNotificationApi;
import in.ac.iiitd.buddyfinder.model.push.Content;
import in.ac.iiitd.buddyfinder.model.push.Data;
import in.ac.iiitd.buddyfinder.model.push.Device;
import in.ac.iiitd.buddyfinder.repository.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
public class PushNotificationController implements PushNotificationApi {

    public final static String GCM_ENDPOINT = "https://android.googleapis.com/gcm/send";
    private final static String API_KEY = "AIzaSyDbLnbW-jq-VroIT8CFf5RJMKsRDVTvZw0";

    @Autowired
    DeviceRepository deviceRepository;

//    public @ResponseBody ResponseEntity<Boolean> registerDevice(@RequestBody String regId) {
//        Device device = new Device(regId);
//        Application.Log("notification registation request - " + regId);
//        if (!deviceRepository.findAll().contains(device)) {
//            deviceRepository.save(device);
//            Application.Log("registered - " + regId);
//            return new ResponseEntity<Boolean>(true, HttpStatus.OK);
//        } else {
//            Application.Log("already registered - " + regId);
//            return new ResponseEntity<Boolean>(false, HttpStatus.FOUND);
//        }
//    }

    @Override
    @RequestMapping(value = PushNotificationApi.REGISTERATION_PATH, method = RequestMethod.POST)
    public
    @ResponseBody
    boolean registerDevice(@RequestBody Device device) {
        Application.Log("registerDevice - notification registation request - " + device.getId());
        return deviceRepository.save(device) != null;
    }

    @RequestMapping(value = PushNotificationApi.REGISTERATION_PATH, method = RequestMethod.GET)
    public
    @ResponseBody
    List<Device> getRegistrationIds() {
        return deviceRepository.findAll();
    }

    @RequestMapping(value = PushNotificationApi.REGISTERATION_PATH, method = RequestMethod.DELETE)
    public
    @ResponseBody
    boolean removeRegisteredDevices(@PathVariable("id") String id) {
//        Device device = new Device(regId);
//        if (regId == null) {
//            deviceRepository.deleteAll();
//            return new ResponseEntity<Boolean>(true, HttpStatus.OK);
//        } else if (deviceRepository.findAll().contains(device)) {
//            deviceRepository.delete(device);
//            return new ResponseEntity<Boolean>(true, HttpStatus.OK);
//        } else {
//            return new ResponseEntity<Boolean>(false, HttpStatus.NOT_FOUND);
//        }
        return false;
    }

    @Override
    @RequestMapping(value = PushNotificationApi.PUSH_NOTIFICATION_SERVICE_PATH, method = RequestMethod.POST)
    public @ResponseBody String pushNotificationToAll(@RequestBody Data data) {
        return pushMulticastNotification(deviceRepository.findAll().stream().map(device -> device.getRegistrationId().replaceAll("\"", "")).collect(Collectors.toList()), data);
    }

    public static String pushMulticastNotification(List<String> regIds, Data data) {
        try {
            List<String> listStrings = regIds;
            Application.Log("pushMulticastNotification - pushing notification - " + data);
            Application.Log("pushMulticastNotification - to devices - " + listStrings);
//            String apiKey = API_KEY;
//            String deviceID=deviceRepository.findAll().get(0).getRegistrationId().replaceAll("\"", "");//Device Id
            Content content = new Content(listStrings);
            content.setData(data);
//            content.createData("Title", "Notification Message");
            URL url = new URL("https://android.googleapis.com/gcm/send");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Authorization", "key=" + API_KEY);
            conn.setDoOutput(true);
            ObjectMapper mapper = new ObjectMapper();
            DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
            Application.Log("pushNotificationToAll - " + mapper.writeValueAsString(content));
            mapper.writeValue(wr, content);
            wr.flush();
            wr.close();

            int responseCode = conn.getResponseCode();
            Application.Log("pushMulticastNotification - " + "\nSending 'POST' request to URL : " + url);
            Application.Log("pushMulticastNotification - " + "Response Code : " + responseCode);

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            Application.Log("pushMulticastNotification - " + response.toString());

//            GcmResponse gcmResponse = mapper.readValue(response, GcmResponse.class);
            return new ObjectMapper().writeValueAsString(response);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }


//    public
//
//    String pushNotificationtoAll(@RequestBody Data data) {
//
//    }

//        GcmService gcmService = new RestAdapter.Builder()
//                .setClient(new ApacheClient(new EasyHttpClient()))
//                .setEndpoint(GCM_ENDPOINT)
//                .setLogLevel(RestAdapter.LogLevel.FULL)
//                .build()
//                .create(GcmService.class);
//
//        List<String> listStrings = new ArrayList<>();
//        for(Device device : deviceRepository.findAll()) {
//            listStrings.add(device.getRegistrationId());
//        }
//
//        GcmNotification gcmNotification = new GcmNotification(data, listStrings);
//
//        Application.Log(gcmNotification.getData().toJSON());
//        Application.Log(gcmNotification.toJSON());
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
