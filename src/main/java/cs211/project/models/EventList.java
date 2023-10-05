package cs211.project.models;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class EventList {
    private ArrayList<Event> events;

    public EventList() {
        events = new ArrayList<>();
    }


    public void addEvent(String name, String picture, String info, String category,
                         String place, LocalDate startDate, LocalDate endDate,
                         LocalTime startTime, LocalTime endTime, String ownerUsername, int maxParticipants,
                         LocalDate startJoinDAte, LocalDate closeJoinDate, String eventUUID) {
        name = name.trim();
        info = info.trim();
        category = category.trim();
        place = place.trim();

        if (!name.equals("") && !info.equals("") && !category.equals("") && !place.equals("")) {
            if (!findEvent(name)) {
                Event newEvent = new Event(name, picture, info, category, place, startDate, endDate,
                        startTime, endTime, ownerUsername, maxParticipants, startJoinDAte, closeJoinDate, eventUUID);
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

