package in.ac.iiitd.buddyfinder.model.object;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rishav Jain on 09-04-2015.
 * Event Information Class
 */
public class Event {

    public static String[] CATEGORIES = {"movie", "coffee", "outstation"};

    private String id;
    private String ownerId;
    private String title;
    private String description;
    private String category;
    private String timeOfCreation;
    private String timeOfEvent;
    private String location;
    private double locationLatitude;
    private double locationLongitude;
    private String timeOfExpiry;

    private List<String> members;
    private String forumId;

    public Event() {

    }

    public Event(String ownerId, String title, String description, String category,
                 String timeOfCreation, String timeOfEvent, String location, double locationLatitude, double locationLongitude, String timeOfExpiry, String forumId) {
        this.ownerId = ownerId;
        this.title = title;
        this.description = description;
        this.category = category;
        this.timeOfCreation = timeOfCreation;
        this.timeOfEvent = timeOfEvent;
        this.location = location;
        this.locationLatitude = locationLatitude;
        this.locationLongitude = locationLongitude;
        this.timeOfExpiry = timeOfExpiry;
        this.members = new ArrayList<String>();
        this.forumId = forumId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTimeOfCreation() {
        return timeOfCreation;
    }

    public void setTimeOfCreation(String timeOfCreation) {
        this.timeOfCreation = timeOfCreation;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public double getLocationLatitude() {
        return locationLatitude;
    }

    public void setLocationLatitude(double locationLatitude) {
        this.locationLatitude = locationLatitude;
    }

    public double getLocationLongitude() {
        return locationLongitude;
    }

    public void setLocationLongitude(double locationLongitude) {
        this.locationLongitude = locationLongitude;
    }

    public String getTimeOfExpiry() {
        return timeOfExpiry;
    }

    public void setTimeOfExpiry(String timeOfExpiry) {
        this.timeOfExpiry = timeOfExpiry;
    }

    public List<String> getMembers() {
        return members;
    }

    public void setMembers(List<String> members) {
        this.members = members;
    }

    public void addMember(String member) {
        this.members.add(member);
    }

    public void removeMember(String member) {
        this.members.remove(member);
    }

    public String getForumId() {
        return forumId;
    }

    public void setForumId(String forumId) {
        this.forumId = forumId;
    }

    @Override
    public String toString() {
        try {
            return new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "<Object to JSON Error>";
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Event)) {
            return false;
        }

        Event e = (Event) obj;
        return this.id.contentEquals(e.getId());
    }

    public String getTimeOfEvent() {
        return timeOfEvent;
    }

    public void setTimeOfEvent(String timeOfEvent) {
        this.timeOfEvent = timeOfEvent;
    }
}
