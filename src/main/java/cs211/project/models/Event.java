package cs211.project.models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;
public class Event implements Comparable {
    private String eventOwnerUsername;
    private String eventName;
    private String eventPicture;
    private String eventInformation;
    private String eventCategory;
    private String placeEvent;
    private LocalDate eventStartDate;
    private LocalDate eventEndDate;
    private int maxParticipants;
    private LocalDate startJoinDate;
    private LocalDate closingJoinDate;
    private ArrayList<Activity> eventActivity;



    public Event(String eventName,String eventPicture, String eventInformation, String eventCategory, String placeEvent,
                 LocalDate eventStartDate, LocalDate eventEndDate, int maxParticipants, LocalDate startJoinDate, LocalDate closingJoinDate) {
        this.eventName = eventName;
        this.eventPicture = eventPicture;
        this.eventInformation = eventInformation;
        this.eventCategory = eventCategory;
        this.placeEvent = placeEvent;
        this.eventStartDate = eventStartDate;
        this.eventEndDate = eventEndDate;
        this.maxParticipants = maxParticipants;
        this.startJoinDate = startJoinDate;
        this.closingJoinDate = closingJoinDate;
    }

    public Event(String eventName,String eventPicture, String eventInformation, String eventCategory, String placeEvent,
                 LocalDate eventStartDate, LocalDate eventEndDate, String eventOwnerUsername) {
        this.eventName = eventName;
        this.eventPicture = eventPicture;
        this.eventInformation = eventInformation;
        this.eventCategory = eventCategory;
        this.placeEvent = placeEvent;
        this.eventStartDate = eventStartDate;
        this.eventEndDate = eventEndDate;
        this.eventActivity = new ArrayList<>();
        this.eventOwnerUsername = eventOwnerUsername;
    }

    public boolean isEvent(String eventName) {
        return this.eventName.equals(eventName);
    }

    public String getEventName() { return eventName; }
    public String getEventInformation() { return eventInformation; }

    public String getEventCategory() { return eventCategory; }

    public String getPlaceEvent() { return placeEvent; }

    public LocalDate getEventStartDate() { return eventStartDate; }

    public LocalDate getEventEndDate() { return eventEndDate; }

    public int getMaxParticipants() { return maxParticipants; }

    public String getEventPicture() {
        return eventPicture;
    }

    public void setMaxParticipant(int maxParticipants) {
        this.maxParticipants = maxParticipants;
    }

    public LocalDate getStartJoinDate() {
        return startJoinDate;
    }

    public void setStartJoinDate(LocalDate startJoinDate) {
        this.startJoinDate = startJoinDate;
    }

    public LocalDate getClosingJoinDate() {
        return closingJoinDate;
    }

    public void setClosingJoinDate(LocalDate closingJoinDate) {
        this.closingJoinDate = closingJoinDate;
    }

    public String getEventOwnerUsername() { return eventOwnerUsername; }

    @Override
    public int compareTo(Object o) {
        Event event = (Event) o;
        try{
            return Integer.parseInt(this.eventName) - Integer.parseInt(event.eventName);
        }
        catch (NumberFormatException e){
            if (this.eventName.compareTo(event.eventName) < 0){
                if (this.eventName.length() > event.eventName.length()){
                    return 1;
                }
            }
            return this.eventName.compareTo(event.eventName);
        }
    }
}
