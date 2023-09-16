package cs211.project.services;

import cs211.project.models.Event;
import cs211.project.models.EventList;
import cs211.project.models.User;

import java.time.LocalDate;
import java.time.ZoneId;

public class CurrentUser {
    //public static CurrentUser.ThisUser ThisUser;
    private static ThisUser user;
    public static ThisUser getUser() {
        return user;
    }
    public static void setUser(String username) {
        user = new ThisUser(username);
    }

    public static class ThisUser extends User {
        EventList currentEvents = new EventList();
        EventList passedEvents = new EventList();
        EventList createdEvents = new EventList();

        public ThisUser(String username) {
            super(username);
            getInfo();
        }

        public EventList getPassedEvents() {
            return passedEvents;
        }
        public EventList getCurrentEvents() {
            return currentEvents;
        }
        public EventList getCreatedEvents() {
            return createdEvents;
        }
        public void addEvent(Event event) {
            createdEvents.addEvent(event);
        }


        public void getInfo() {
            Datasource<EventList> datasource = new EventListFileDatasource("data", "eventList.csv");
            EventList allEvents = datasource.readData();
            ZoneId thaiTimeZone = ZoneId.of("Asia/Bangkok");
            LocalDate currentDate = LocalDate.now(thaiTimeZone);

            for (Event event : allEvents.getEvents()) {
                if (event.getEventEndDate().isBefore(currentDate)) {
                    passedEvents.addEvent(event);
                } else {
                    currentEvents.addEvent(event);
                }
                if (event.getEventOwnerUsername().equals(this.getUsername())) {
                    createdEvents.addEvent(event);
                }
            }
        }
    }
}
