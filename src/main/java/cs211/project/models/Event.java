package cs211.project.models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;
public class Event {
    private String eventName;
    private String eventInformation;
    private String eventCategory;
    private String placeEvent;
    private LocalDate eventStartDate;
    private LocalDate eventEndDate;
    private int maxParticipants;
    private LocalDate startJoinDate;
    private LocalDate closingJoinDate;


    public Event(String eventName, String eventInformation, String eventCategory, String placeEvent,
                 LocalDate eventStartDate, LocalDate eventEndDate) {
        this.eventName = eventName;
        this.eventInformation = eventInformation;
        this.eventCategory = eventCategory;
        this.placeEvent = placeEvent;
        this.eventStartDate = eventStartDate;
        this.eventEndDate = eventEndDate;
    }

    public Event(String eventName, String eventInformation, String eventCategory, String placeEvent,
                 LocalDate eventStartDate, LocalDate eventEndDate, int maxParticipants, LocalDate startJoinDate, LocalDate closingJoinDate) {
        this.eventName = eventName;
        this.eventInformation = eventInformation;
        this.eventCategory = eventCategory;
        this.placeEvent = placeEvent;
        this.eventStartDate = eventStartDate;
        this.eventEndDate = eventEndDate;
        this.maxParticipants = maxParticipants;
        this.startJoinDate = startJoinDate;
        this.closingJoinDate = closingJoinDate;
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

}
