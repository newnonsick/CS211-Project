package cs211.project.models;
import java.time.LocalDate;
import java.util.ArrayList;

public class EventList {
    private ArrayList<Event> events;

    public EventList() {
        events = new ArrayList<>();
    }


    public void addEvent(String eventName, String eventPicture, String eventInformation, String eventCategory, String placeEvent, LocalDate eventStartDate,
                         LocalDate eventEndDate, String eventOwnerUsername, int maxParticipants, LocalDate startJoinDAte, LocalDate closingJoinDate, String eventUUID) {
        eventName = eventName.trim();
        eventInformation = eventInformation.trim();
        eventCategory = eventCategory.trim();
        placeEvent = placeEvent.trim();

        if (!eventName.equals("") && !eventInformation.equals("") && !eventCategory.equals("") && !placeEvent.equals("")) {
            if (!findEvent(eventName)) {
                Event newEvent = new Event(eventName, eventPicture, eventInformation, eventCategory, placeEvent, eventStartDate, eventEndDate, eventOwnerUsername, maxParticipants, startJoinDAte, closingJoinDate, eventUUID);
                events.add(newEvent);
            }
        }
    }

    public boolean findEvent(String eventName) {
        for (Event anEvent : this.events) {
            if (eventName.equals(anEvent.getName())) {
                return true;
            }
        } return false;
    }



    public Event findEventByEventName(String eventName){
        for (Event anEvent : this.events) {  //for-each loop
            if (eventName.equals(anEvent.getName())) {
                return anEvent;
            }
        }
        return null;
    }

    public Event findEventByUUID(String eventUUID){
        for (Event event : this.events) {
            if (eventUUID.equals(event.getEventUUID())) {
                return event;
            }
        }
        return null;
    }


    public ArrayList<Event> getEvents(){
        return events;
    }

}

