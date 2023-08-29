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
            Event existingEvent = findEventByEventName(eventName);
            if (existingEvent == null) {
                Event newEvent = new Event(eventName, eventInformation, eventCategory, placeEvent, eventStartDate, eventEndDate);
                events.add(newEvent);
            }
        }

    }

    public Event findEventByEventName(String eventName) {
        for (Event event : this.events) {
            if (event.getEventName().equals(eventName)) {
                return event;
            }
        }
        return null;
    }

    public ArrayList<Event> getEvents(){
        return events;
    }



    }
