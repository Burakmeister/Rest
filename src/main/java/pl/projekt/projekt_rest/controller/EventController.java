package pl.projekt.projekt_rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.projekt.projekt_rest.exception.EventNotFoundException;
import pl.projekt.projekt_rest.exception.NotEnoughParamsException;
import pl.projekt.projekt_rest.model.Event;
import pl.projekt.projekt_rest.model.EventType;
import pl.projekt.projekt_rest.service.EventService;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/events")
public class EventController {
    final private EventService eventService;

    @Autowired
    public EventController(EventService eventService){
        this.eventService = eventService;
    }

    @GetMapping
    public ResponseEntity<List<Event>> getAllEvents(){
        try {
            List<Event> events = eventService.getAllEvents();
            return new ResponseEntity<>(events, HttpStatus.OK);
        } catch (NotEnoughParamsException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Event> getEventsById(@PathVariable long id){
        try{
            Event event = eventService.getEventById(id);
            return new ResponseEntity<>(event, HttpStatus.OK);
        }catch (EventNotFoundException ex){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/byDay")
    public ResponseEntity<List<Event>> getEventsByDay(@RequestParam(name = "day") String day,
                                                      @RequestParam(name = "month") String month,
                                                      @RequestParam(name = "year") String year){
        try{
            int dayInt = Integer.parseInt(day);
            int monthInt = Integer.parseInt(month);
            int yearInt = Integer.parseInt(year);
            List<Event> events = eventService.getEventsByDay(
                    yearInt,
                    monthInt,
                    dayInt);

            return new ResponseEntity<>(events,
                    HttpStatus.OK);
        }catch (NotEnoughParamsException | NumberFormatException e){
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @GetMapping("/byWeek")
    public ResponseEntity<List<Event>> getEventsByWeek(@RequestParam(name = "week") String week,
                                                      @RequestParam(name = "month") String month,
                                                      @RequestParam(name = "year") String year){
        try{
            int weekInt = Integer.parseInt(week);
            int monthInt = Integer.parseInt(month);
            int yearInt = Integer.parseInt(year);
            List<Event> events = eventService.getEventsByWeek(
                    yearInt,
                    monthInt,
                    weekInt);
            return new ResponseEntity<>(events,
                    HttpStatus.OK);
        }catch (NotEnoughParamsException | NumberFormatException e){
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @GetMapping("/types")
    public ResponseEntity<List<EventType>> getEventTypes(){
        return new ResponseEntity<>(eventService.getEventTypes(), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<Event> createEvent(@RequestBody Event event){
        Event newEvent = eventService.createEvent(event);
        newEvent.add(linkTo(methodOn(EventController.class).getEventsById(newEvent.getEventId())).withSelfRel());
        newEvent.add(linkTo(methodOn(EventController.class).updateEvent(newEvent.getEventId(), newEvent)).withRel("update"));
        return new ResponseEntity<>(newEvent, HttpStatus.CREATED);
    }

    @PutMapping(path = "/{id}/update")
    public ResponseEntity<Event> updateEvent(@PathVariable long id, @RequestBody Event request){
        try {
            Event event = eventService.updateEvent(id, request);
            return new ResponseEntity<>(event, HttpStatus.OK);
        } catch (EventNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
