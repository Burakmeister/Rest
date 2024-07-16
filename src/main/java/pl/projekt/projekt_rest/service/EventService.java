package pl.projekt.projekt_rest.service;

import org.springframework.stereotype.Service;
import pl.projekt.projekt_rest.controller.EventController;
import pl.projekt.projekt_rest.exception.EventNotFoundException;
import pl.projekt.projekt_rest.exception.NotEnoughParamsException;
import pl.projekt.projekt_rest.model.Event;
import pl.projekt.projekt_rest.model.EventType;

import java.util.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class EventService {
    Map<Long, Event> events = new HashMap<>();

    public EventService(){
        events.put(0L, new Event(0L, "Koncert scholi „Winnica”", EventType.CONCERT, new Date(1717236000000L), "W Sanktuarium Najświętszego Sakramentu w Sokółce odbędzie się Archidiecezjalny Dzień Dziecka. Na spotkanie do Sokółki, wspólną modlitwę i dzielenie się radością abp Józef Guzdek zaprasza wszystkie dzieci z archidiecezji wraz z rodzicami, kapłanami, wychowawcami oraz katechetami.", 1, 6, 2024));
        events.put(1L, new Event(1L, "Film „Tyle co nic”", EventType.EXHIBIT, new Date(1717696800000L), "Film osadzony jest w realiach polskiej wsi. Twórcy z wrażliwością i subtelnym wyczuciem kreślą portret człowieka stojącego w obliczu moralnego dylematu, co czyni film nie tylko dziełem rozrywkowym, ale prawdziwym manifestem humanistycznym. Kreacja głównego bohatera Artura Poczesnego, nagrodzona na Festiwalu Polskich Filmów Fabularnych w Gdyni stanowi dowód na niezwykły talent aktora i umiejętność przekazu emocji.", 1, 6, 2024));
        events.put(2L, new Event(2L, "Festyn rodzinny", EventType.PICNIC_FESTIVAL, new Date(1717934400000L), "W Jałówce (gmina Dąbrowa Białostocka) odbędzie się festyn rodzinny. Wydarzenie rozpocznie się mszą świętą, a później przyjdzie czas na zabawę.", 2, 6, 2024));
        events.put(3L, new Event(3L, "Festyn pod nazwą „Bezpieczne wakacje”", EventType.PICNIC_FESTIVAL, new Date(1718539200000L), "W programie: bieg na sto metrów pn. „Setka na stulecie banku” w różnych kategoriach wiekowych, od przedszkolaka do seniora; spotkanie z magikiem, animacje, zjeżdżalnie, malowanie twarzy. W planach są też turnieje piłki nożnej dla Ochotniczych Straży Pożarnych i klubów piłkarskich, pokaz musztry paradnej oraz pokaz strażacki i ratowniczy.", 3, 6, 2024));
        events.put(4L, new Event(4L, "Dyskusyjny Klub Książki", EventType.MEETING, new Date(1718020800000L), "Spotkanie w ramach Dyskusyjnego Klubu Książki. Filia Biblioteczna nr 1 w Sokółce", 2, 6, 2024));
        for(Event event: events.values()){
            event.add(linkTo(methodOn(EventController.class).getEventsById(event.getEventId())).withSelfRel());
            event.add(linkTo(methodOn(EventController.class).updateEvent(event.getEventId(), event)).withRel("update"));
        }
    }

    public List<Event> getAllEvents() throws NotEnoughParamsException {
        if(!this.events.isEmpty()){
            return this.events.values().stream().toList();
        }else{
            throw new NotEnoughParamsException("Brak wydarzeń!");
        }
    }

    public List<Event> getEventsByDay(int year, int month, int day) throws NotEnoughParamsException {
        if(year!=0 && month!=0 && day!=0) {
            return events.values()
                    .stream()
                    .filter(
                            (event) -> event.getYear() == year && event.getMonth() == month && (((event.getDate().getDay()) == day && event.getDate().getDay() != 0) || (event.getDate().getDay() == 0 && day == 7))
                    ).toList();
        }
        else{
            throw new NotEnoughParamsException("Niepełna lista parametrów!");
        }
    }

    public List<Event> getEventsByWeek(int year, int month, int week) throws NotEnoughParamsException {
        if(year!=0 && month!=0 && week!=0){
            return events.values()
                    .stream()
                    .filter(
                            (event)-> event.getYear()==year && event.getMonth()==month && event.getWeek()==week
                    ).toList();
        }
        else{
            throw new NotEnoughParamsException("Niepełna lista parametrów!");
        }
    }

    public Event createEvent(Event event){
        event.setEventId(events.size()+1);
        events.put(event.getEventId(), event);
        return events.get(event.getEventId());
    }

    public Event updateEvent(long id, Event event) throws EventNotFoundException {
        if(events.size()>id) {
            Event eventToEdit = events.get(id);
            if (event.getName() != null) {
                eventToEdit.setName(event.getName());
            }
            if (event.getDescription() != null) {
                eventToEdit.setDescription(event.getDescription());
            }
            if (event.getDate() != null) {
                eventToEdit.setDate(event.getDate());
            }
            if (event.getType() != null) {
                eventToEdit.setType(event.getType());
            }
            if (event.getWeek() != 0) {
                eventToEdit.setWeek(event.getWeek());
            }
            if (event.getMonth() != 0) {
                eventToEdit.setMonth(event.getMonth());
            }
            if (event.getYear() != 0) {
                eventToEdit.setYear(event.getYear());
            }
            return events.get(eventToEdit.getEventId());
        }else {
            throw new EventNotFoundException("Brak wydarzenia o podanym id!");
        }
    }
    public Event getEventById(long id) throws EventNotFoundException {
        if(events.size()>id){
            return events.get(id);
        }else{
            throw new EventNotFoundException("Brak wydarzenia o podanym id!");
        }
    }
    public List<EventType> getEventTypes(){
        return Arrays.asList(EventType.values());
    }
}
