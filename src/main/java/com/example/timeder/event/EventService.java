package com.example.timeder.event;

import com.example.timeder.exception.ResourceNotFoundException;
import com.example.timeder.user.User;
import com.example.timeder.user.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventService {

    private final EventRepository eventRepository;
    private final UserRepository userRepository;


    public EventService(EventRepository eventRepository, UserRepository userRepository) {
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
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
    public User addUserToEvent(int eventId, UserEventDTO userEventDTO) throws ResourceNotFoundException {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found"));

        Optional<User> userFromDb =  userRepository.findByIndex(userEventDTO.getIndex());
        if(userFromDb.isEmpty()) {
            throw new ResourceNotFoundException("User not found");
        }

        event.getMembers().add(userFromDb.get());
        eventRepository.save(event);

        return userFromDb.get();
    }

    // TODO Dodanie grupy do eventu na podstawie nazwy grupy

    // DELETE

    public void deleteEvent(int id) throws ResourceNotFoundException {
        if (!this.eventRepository.existsById(id)) {
            throw new ResourceNotFoundException("Event not found");
        }

        this.eventRepository.deleteById(id);
    }

}
