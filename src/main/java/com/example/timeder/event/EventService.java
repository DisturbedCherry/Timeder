package com.example.timeder.event;

import com.example.timeder.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {

    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    // CREATE

    public Event createEvent(EventDTO eventDTO) {
        Event newEvent = new Event(eventDTO.getName(), eventDTO.getStartDateTime(), eventDTO.getIsPrivate(), eventDTO.getDescription(), eventDTO.getLocalization(), eventDTO.getPhotoFilePath());
        this.eventRepository.save(newEvent);
        return newEvent;
    }

    // READ

    public List<Event> getEvents() {
        return this.eventRepository.findAll();
    }

    public Event getEvent(int id) throws ResourceNotFoundException {
        return this.eventRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Event not found"));
    }

    // UPDATE

    public Event updateEvent(int id, EventDTO eventDTO) throws ResourceNotFoundException {
        if (!this.eventRepository.existsById(id)) {
            throw new ResourceNotFoundException("Event not found");
        }

        Event updatedEvent = this.eventRepository.getReferenceById(id);

        if (eventDTO.getName() != null) {
            updatedEvent.setName(eventDTO.getName());
        }
        if (eventDTO.getStartDateTime() != null) {
            updatedEvent.setStartDateTime(eventDTO.getStartDateTime());
        }
        if (eventDTO.getIsPrivate() != null) {
            updatedEvent.setIsPrivate(eventDTO.getIsPrivate());
        }
        if (eventDTO.getDescription() != null) {
            updatedEvent.setDescription(eventDTO.getDescription());
        }
        if (eventDTO.getLocalization() != null) {
            updatedEvent.setLocalization(eventDTO.getLocalization());
        }
        if (eventDTO.getPhotoFilePath() != null) {
            updatedEvent.setPhotoFilePath(eventDTO.getPhotoFilePath());
        }

        return this.eventRepository.save(updatedEvent);
    }

    // TODO Dodanie użytkownika do eventu na podstawie indeksu, imienia lub nazwiska
    // TODO Dodanie grupy do eventu na podstawie nazwy grupy

    // REMOVE

    public void deleteEvent(int id) throws ResourceNotFoundException {
        if (!this.eventRepository.existsById(id)) {
            throw new ResourceNotFoundException("Event not found");
        }

        this.eventRepository.deleteById(id);
    }

}
