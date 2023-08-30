package cs211.project.models;

public class Event {
    private String eventName;
    private String eventInformation;
    private String eventCategory;
    private String placeEvent;
    private String eventStartDate;
    private String eventEndDate;
    private String closingJoinDate;
    private int maxParticipants;

    public Event(String eventName, String eventInformation, String eventCategory, String placeEvent,
                 String eventStartDate, String eventEndDate) {
        this.eventName = eventName;
        this.eventInformation = eventInformation;
        this.eventCategory = eventCategory;
        this.placeEvent = placeEvent;
        this.eventStartDate = eventStartDate;
        this.eventEndDate = eventEndDate;
    }

    public boolean isEvent(String eventName) { return this.eventName.equals(eventName); }

    public String getEventName() { return eventName; }

    public String getEventInformation() { return eventInformation; }

    public String getEventCategory() { return eventCategory; }

    public String getPlaceEvent() { return placeEvent; }

    public String getEventStartDate() { return eventStartDate; }

    public String getEventEndDate() { return eventEndDate; }

    public String getClosingJoinDate() { return closingJoinDate; }

    public int getMaxParticipants() { return maxParticipants; }
}

