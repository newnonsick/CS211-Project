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
    private String eventUUID;



    public Event(String eventName,String eventPicture, String eventInformation, String eventCategory, String placeEvent,
                 LocalDate eventStartDate, LocalDate eventEndDate, String eventOwnerUsername, int maxParticipants, LocalDate startJoinDate, LocalDate closingJoinDate, String eventUUID) {
        this.eventName = eventName;
        this.eventPicture = eventPicture;
        this.eventInformation = eventInformation;
        this.eventCategory = eventCategory;
        this.placeEvent = placeEvent;
        this.eventStartDate = eventStartDate;
        this.eventEndDate = eventEndDate;
        this.eventOwnerUsername = eventOwnerUsername;
        this.maxParticipants = maxParticipants;
        this.startJoinDate = startJoinDate;
        this.closingJoinDate = closingJoinDate;
        this.eventUUID = eventUUID;
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
    public String getEventUUID() { return eventUUID; }

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

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public void setEventPicture(String eventPicture) {
        this.eventPicture = eventPicture;
    }

    public void setEventInformation(String eventInformation) {
        this.eventInformation = eventInformation;
    }

    public void setEventCategory(String eventCategory) {
        this.eventCategory = eventCategory;
    }

    public void setPlaceEvent(String placeEvent) {
        this.placeEvent = placeEvent;
    }

    public void setEventStartDate(LocalDate eventStartDate) {
        this.eventStartDate = eventStartDate;
    }

    public void setEventEndDate(LocalDate eventEndDate) {
        this.eventEndDate = eventEndDate;
    }


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
