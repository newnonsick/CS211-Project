package cs211.project.models;
import java.time.LocalDate;
import java.util.ArrayList;

public class EventList {
    private ArrayList<Event> events;

    public EventList() {
        events = new ArrayList<>();
    }


    public void addEvent(String eventName, String eventPicture, String eventInformation, String eventCategory, String placeEvent, LocalDate eventStartDate,
                         LocalDate eventEndDate, String eventOwnerUsername, int maxParticipants, LocalDate startJoinDAte, LocalDate closingJoinDate) {
        eventName = eventName.trim();
        eventInformation = eventInformation.trim();
        eventCategory = eventCategory.trim();
        placeEvent = placeEvent.trim();

        if (!eventName.equals("") && !eventInformation.equals("") && !eventCategory.equals("") && !placeEvent.equals("")) {
            if (!findEvent(eventName)) {
                Event newEvent = new Event(eventName, eventPicture, eventInformation, eventCategory, placeEvent, eventStartDate, eventEndDate, eventOwnerUsername, maxParticipants, startJoinDAte, closingJoinDate);
                events.add(newEvent);
            }
        }
    }

    public boolean findEvent(String eventName) {
        for (Event anEvent : this.events) {
            if (eventName.equals(anEvent.getEventName())) {
                return true;
            }
        } return false;
    }



    public Event findEventByEventName(String eventName){
        for (Event anEvent : this.events) {  //for-each loop
            if (eventName.equals(anEvent.getEventName())) {
                return anEvent;
            }
        }
        return null;
    }


    public ArrayList<Event> getEvents(){
        return events;
    }

}

