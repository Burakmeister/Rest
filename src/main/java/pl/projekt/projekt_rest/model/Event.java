package pl.projekt.projekt_rest.model;

import org.springframework.hateoas.RepresentationModel;

import java.util.Date;

public class Event extends RepresentationModel<Event> {
    private long eventId;
    private String name;
    private EventType type;
    private Date date;
    private String description;
    private int week;
    private int month;
    private int year;

    public Event(long eventId, String name, EventType type, Date date, String description, int week, int month, int year) {
        this.eventId = eventId;
        this.name = name;
        this.type = type;
        this.date = date;
        this.description = description;
        this.month = month;
        this.week = week;
        this.year = year;
    }

    public Event() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public EventType getType() {
        return type;
    }

    public void setType(EventType type) {
        this.type = type;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public long getEventId() {
        return eventId;
    }

    public void setEventId(long eventId) {
        this.eventId = eventId;
    }
}
