package com.example.timeder.event;

import com.example.timeder.exception.ResourceNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/events")
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("/")
    public List<Event> getEvents() {
        return this.eventService.getEvents();
    }

    @GetMapping("/{id}")
    public Event getEvent(@PathVariable int id) {
        try {
            return this.eventService.getEvent(id);
        } catch (ResourceNotFoundException e) {
            return null;
        }
    }

    @PutMapping("/{id}")
    public Event updateEvent(@PathVariable int id, @RequestBody EventDTO eventDTO) {
        try {
            return this.eventService.updateEvent(id, eventDTO);
        } catch (ResourceNotFoundException e) {
            return null;
        }
    }

    @PostMapping("/")
    public Event createEvent(@RequestBody EventDTO eventDTO) {
        return this.eventService.createEvent(eventDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteEvent(@PathVariable int id) {
        try {
            this.eventService.deleteEvent(id);
        } catch (Exception ignore) {
        }
    }

}
