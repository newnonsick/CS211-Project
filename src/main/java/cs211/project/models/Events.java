package cs211.project.models;
import java.util.ArrayList;
public class Events {
    private ArrayList<Event> events;

    public Events() {
        events = new ArrayList<>();
    }

    public void addNewEvent(String eventName, String eventInformation, String eventCategory, String placeEvent, String eventStartDate,
                            String eventEndDate) {
        eventName = eventName.trim();
        eventInformation = eventInformation.trim();
        eventCategory = eventCategory.trim();
        placeEvent = placeEvent.trim();

        if (!eventName.equals("") && !eventInformation.equals("") && !eventCategory.equals("") && !placeEvent.equals("")) {
            Event existingEvent = findEvent(eventName);
            if (existingEvent == null) {
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

    public ArrayList<Event> getEvents(){
        return events;
    }



    }
