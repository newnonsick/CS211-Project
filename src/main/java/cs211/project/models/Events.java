package cs211.project.models;
import java.time.LocalDate;
import java.util.ArrayList;

public class Events {
    private ArrayList<Event> events;

    public Events() {
        events = new ArrayList<>();
    }

    public void addEvent(Event event) {
        events.add(event);
    }
    public void addEvent(String eventName, String eventInformation, String eventCategory, String placeEvent, LocalDate eventStartDate,
                         LocalDate eventEndDate) {
        eventName = eventName.trim();
        eventInformation = eventInformation.trim();
        eventCategory = eventCategory.trim();
        placeEvent = placeEvent.trim();

        if (!eventName.equals("") && !eventInformation.equals("") && !eventCategory.equals("") && !placeEvent.equals("")) {
            if (!findEvent(eventName)) {
                Event newEvent = new Event(eventName, eventInformation, eventCategory, placeEvent, eventStartDate, eventEndDate);
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

    public boolean findEvent(Event event) {
        for (Event anEvent : this.events) {
            if (event.getEventName().equals(anEvent.getEventName())) {
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

