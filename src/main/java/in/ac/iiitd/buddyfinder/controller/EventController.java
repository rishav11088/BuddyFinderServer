package in.ac.iiitd.buddyfinder.controller;

import in.ac.iiitd.buddyfinder.Application;
import in.ac.iiitd.buddyfinder.model.client.EventServiceApi;
import in.ac.iiitd.buddyfinder.model.object.Event;
import in.ac.iiitd.buddyfinder.model.object.Forum;
import in.ac.iiitd.buddyfinder.model.object.ForumMessage;
import in.ac.iiitd.buddyfinder.model.object.User;
import in.ac.iiitd.buddyfinder.model.push.Data;
import in.ac.iiitd.buddyfinder.model.push.Device;
import in.ac.iiitd.buddyfinder.repository.DeviceRepository;
import in.ac.iiitd.buddyfinder.repository.EventRepository;
import in.ac.iiitd.buddyfinder.repository.ForumRepository;
import in.ac.iiitd.buddyfinder.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import retrofit.http.Query;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rishav Jain on 19-04-2015.
 * EventController
 */
@Controller
public class EventController implements EventServiceApi {

    @Autowired
    EventRepository eventRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ForumRepository forumRepository;

    @Autowired
    DeviceRepository deviceRepository;

    @Override
    @RequestMapping(value = EVENT_SERVICE_PATH, method = RequestMethod.POST)
    public
    @ResponseBody
    boolean addEvent(@RequestBody Event event) {
        event.setMembers(new ArrayList<>());

        Forum forum = forumRepository.save(new Forum());
        event.setForumId(forum.getId());

        event = eventRepository.save(event);

        if (event != null) {
            Application.Log("addEvent - event added -\n" + event.getId());
            User user = userRepository.findById(event.getOwnerId());

            if (user != null) {
                user.addEvent(event.getId());
                if (userRepository.save(user) != null) {
                    Application.Log("user modified -\n" + user.getId());
                    event.addMember(user.getId());

                    if (eventRepository.save(event) != null) {
                        Application.Log("addEvent - event modified -\n" + event.getId());
                        return true;
                    } else {
                        eventRepository.delete(event.getId());
                    }
                }
            }
        }

        Application.Log("addEvent - error in adding event");
        return false;
    }

    @Override
    @RequestMapping(value = EVENT_SERVICE_PATH, method = RequestMethod.GET)
    public
    @ResponseBody
    List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    @Override
    public boolean deleteEvent(@Query(EVENT_ID_QUERY_PARAMETER) String id) {
        return false;
    }

    @Override
    @RequestMapping(value = EVENT_ID_SEARCH_PATH, method = RequestMethod.GET)
    public
    @ResponseBody
    Event getEventById(@RequestParam(EVENT_ID_QUERY_PARAMETER) String id) {
        return eventRepository.findById(id);
    }

    @Override
    @RequestMapping(value = EVENT_CATEGORY_SEARCH_PATH, method = RequestMethod.GET)
    public
    @ResponseBody
    List<Event> getEventsByCategory(@RequestParam(EVENT_CATEGORY_QUERY_PARAMETER) String category) {
        return eventRepository.findByCategory(category);
    }

    @Override
    @RequestMapping(value = EVENT_MEMBER_SERVICE_PATH, method = RequestMethod.POST)
    public
    @ResponseBody
    boolean addMemberToEvent(@RequestParam(EVENT_ID_QUERY_PARAMETER) String eventId, @RequestParam(MEMBER_ID_QUERY_PARAMETER) String memberId) {
        Event event = eventRepository.findById(eventId);

        if (event != null) {
            User user = userRepository.findById(memberId);

            if (user != null) {
                Application.Log("addMemberToEvent - adding event to user - " + user.getId());
                user.addEvent(eventId);

                if (userRepository.save(user) != null) {
                    Application.Log("addMemberToEvent - adding member to event - " + event.getId());
                    List<String> previousMembersList = new ArrayList<>();

                    for(String member : event.getMembers()) {
                        previousMembersList.add(member);
                    }

                    event.addMember(memberId);

                    User member = userRepository.findById(memberId);

                    if (eventRepository.save(event) != null) {
                        sendPushNotification(previousMembersList, new Data("member_add", eventId,
                                member.getFirstName() + " joined event : " + event.getTitle()));
                        return true;
                    }
                }
            }
        }

        Application.Log("addMemberToEvent - error adding member");
        return false;
    }

    @Override
    @RequestMapping(value = EVENT_FORUM_SERVICE_PATH, method = RequestMethod.GET)
    public
    @ResponseBody
    List<ForumMessage> getForumMessages(@PathVariable(EVENT_ID_QUERY_PARAMETER) String eventId) {
        Application.Log("getForumMessages - request for forum messages - " + eventId);
        return forumRepository.findById(eventRepository.findById(eventId).getForumId()).getForumMessages();
    }

    @Override
    @RequestMapping(value = EVENT_FORUM_SERVICE_PATH, method = RequestMethod.POST)
    public @ResponseBody boolean sendForumMessage(@PathVariable(EVENT_ID_QUERY_PARAMETER) String eventId, @RequestBody ForumMessage forumMessage) {
        Event event = eventRepository.findById(eventId);
        Forum forum = forumRepository.findById(event.getForumId());
        forum.addForumMessage(forumMessage);
        forumRepository.save(forum);

        User sender = userRepository.findById(forumMessage.getSender());

        List<String> membersList = event.getMembers();
        membersList.remove(forumMessage.getSender());
        sendPushNotification(membersList, new Data("new_message", eventId, sender.getFirstName() + ": " + forumMessage.getMessage()));

        Application.Log("sendForumMessage - message recieved at event - " + eventId + " - " + forumMessage);
        return true;
    }

    public void sendPushNotification(List<String> membersList, Data data) {
        if(membersList.size() > 0) {
            new Thread() {
                @Override
                public void run() {
                    List<Device> devices = deviceRepository.findAll();
                    List<String> toSend = new ArrayList<>();

                    for (Device device : devices) {
                        if (membersList.contains(device.getId())) {
                            toSend.add(device.getRegistrationId());
                        }
                    }

                    Application.Log("sendPushNotification - sending notification - " + data + " - to - " + membersList);
                    PushNotificationController.pushMulticastNotification(toSend, data);
                }
            }.start();
        }
    }
}
