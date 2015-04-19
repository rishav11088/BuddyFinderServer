package in.ac.iiitd.buddyfinder.controller;

import in.ac.iiitd.buddyfinder.model.client.EventServiceApi;
import in.ac.iiitd.buddyfinder.model.object.Event;
import in.ac.iiitd.buddyfinder.model.object.Forum;
import in.ac.iiitd.buddyfinder.model.object.User;
import in.ac.iiitd.buddyfinder.repository.EventRepository;
import in.ac.iiitd.buddyfinder.repository.ForumMessageRepository;
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
    ForumMessageRepository forumMessageRepository;

    @Override
    @RequestMapping(value = EVENT_SERVICE_PATH, method = RequestMethod.POST)
    public
    @ResponseBody
    boolean addEvent(@RequestBody Event event) {
        event.setMembers(new ArrayList<>());

        Forum forum = forumMessageRepository.save(new Forum());
        event.setForumId(forum.getId());

        event = eventRepository.save(event);

        if (event != null) {
            System.out.println("event addded -\n" + event);
            User user = userRepository.findById(event.getOwnerId());

            if (user != null) {
                user.addEvent(event.getId());
                if (userRepository.save(user) != null) {
                    System.out.println("user modified -\n" + user);
                    event.addMember(user.getId());

                    if (eventRepository.save(event) != null) {
                        System.out.println("event modified -\n" + event);
                        return true;
                    } else {
                        eventRepository.delete(event.getId());
                    }
                }
            }
        }

        System.out.println("error in adding event");
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
            System.out.println("adding member to event - " + event);
            event.addMember(memberId);
            if (eventRepository.save(event) != null) {
                return true;
            }
        }

        System.out.println("error adding member");
        return false;
    }
}
