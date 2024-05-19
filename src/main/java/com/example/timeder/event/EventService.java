package com.example.timeder.event;

import com.example.timeder.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
        return this.eventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found"));
    }

    // UPDATE

    public void updateEventName(int id, String name) throws ResourceNotFoundException {
        if (!this.eventRepository.existsById(id)) {
            throw new ResourceNotFoundException("Event not found");
        }

        Event updatedEvent = this.eventRepository.getReferenceById(id);
        updatedEvent.setName(name);
        this.eventRepository.save(updatedEvent);
    }

    public void updateEventStartDateTime(int id, LocalDate startDateTime) throws ResourceNotFoundException {
        if (!this.eventRepository.existsById(id)) {
            throw new ResourceNotFoundException("Event not found");
        }

        Event updatedEvent = this.eventRepository.getReferenceById(id);
        updatedEvent.setStartDateTime(startDateTime);
        this.eventRepository.save(updatedEvent);
    }

    public void updateEventIsPrivate(int id, Boolean isPrivate) throws ResourceNotFoundException {
        if (!this.eventRepository.existsById(id)) {
            throw new ResourceNotFoundException("Event not found");
        }

        Event updatedEvent = this.eventRepository.getReferenceById(id);
        updatedEvent.setIsPrivate(isPrivate);
        this.eventRepository.save(updatedEvent);
    }

    public void updateEventDescription(int id, String description) throws ResourceNotFoundException {
        if (!this.eventRepository.existsById(id)) {
            throw new ResourceNotFoundException("Event not found");
        }

        Event updatedEvent = this.eventRepository.getReferenceById(id);
        updatedEvent.setDescription(description);
        this.eventRepository.save(updatedEvent);
    }

    public void updateEventLocalization(int id, String localization) throws ResourceNotFoundException {
        if (!this.eventRepository.existsById(id)) {
            throw new ResourceNotFoundException("Event not found");
        }

        Event updatedEvent = this.eventRepository.getReferenceById(id);
        updatedEvent.setLocalization(localization);
        this.eventRepository.save(updatedEvent);
    }

    public void updateEventPhotoFilePath(int id, String photoFilePath) throws ResourceNotFoundException {
        if (!this.eventRepository.existsById(id)) {
            throw new ResourceNotFoundException("Event not found");
        }

        Event updatedEvent = this.eventRepository.getReferenceById(id);
        updatedEvent.setLocalization(photoFilePath);
        this.eventRepository.save(updatedEvent);
    }

    // TODO Jedna metoda updateEvent
    // TODO Dodanie uytkownika do eventu na podstawie np, indeksu, imienia i nazwiska
    // TODO Dodanie grupy do eventu na podstawie np. nazwy grupy

    // REMOVE

    public void deleteEvent(int id) throws ResourceNotFoundException {
        if (!this.eventRepository.existsById(id)) {
            throw new ResourceNotFoundException("Event not found");
        }

        this.eventRepository.deleteById(id);
    }
}
