package com.example.timeder.event;

import com.example.timeder.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/events")
@CrossOrigin(origins = "http://localhost:3000")
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping("/")
    public ResponseEntity<EventDTO> createEvent(@RequestBody EventDTO eventDTO) {
        try {
            EventDTO createdEvent = this.eventService.createEvent(eventDTO);
            return new ResponseEntity<>(createdEvent, HttpStatus.CREATED);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/")
    public ResponseEntity<List<EventDTO>> getEvents() {
        List<EventDTO> events = this.eventService.getEvents();
        return new ResponseEntity<>(events, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventDTO> getEvent(@PathVariable int id) {
        try {
            EventDTO event = this.eventService.getEvent(id);
            return new ResponseEntity<>(event, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}/users")
    public ResponseEntity<List<UserEventDTO>> getEventMembers(@PathVariable int id) {
        try {
            List<UserEventDTO> members = this.eventService.getEventMembers(id);
            return new ResponseEntity<>(members, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}/groups")
    public ResponseEntity<List<GroupEventDTO>> getEventGroups(@PathVariable int id) {
        try {
            List<GroupEventDTO> groups = this.eventService.getEventGroups(id);
            return new ResponseEntity<>(groups, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/users")
    public ResponseEntity<UserEventDTO> addUserToEvent(@RequestBody CreateUserEventDTO createUserEventDTO) {
        try {
            UserEventDTO addedUser = this.eventService.addUserToEvent(createUserEventDTO);
            return new ResponseEntity<>(addedUser, HttpStatus.CREATED);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/groups")
    public ResponseEntity<GroupEventDTO> addGroupToEvent(@RequestBody CreateGroupEventDTO createGroupEventDTO) {
        try {
            GroupEventDTO addedGroup = this.eventService.addGroupToEvent(createGroupEventDTO);
            return new ResponseEntity<>(addedGroup, HttpStatus.CREATED);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventDTO> updateEvent(@PathVariable int id, @RequestBody EventDTO eventDTO) {
        try {
            EventDTO updatedEvent = this.eventService.updateEvent(id, eventDTO);
            return new ResponseEntity<>(updatedEvent, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEvent(@PathVariable int id) {
        try {
            this.eventService.deleteEvent(id);
            return new ResponseEntity<>("Event deleted successfully", HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>("Event not found", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/users")
    public ResponseEntity<String> deleteEventMember(@RequestBody DeleteUserEventDTO deleteUserEventDTO) {
        try {
            this.eventService.deleteMember(deleteUserEventDTO);
            return new ResponseEntity<>("Member deleted successfully", HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>("Member not found", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/groups")
    public ResponseEntity<String> deleteEventGroup(@RequestBody DeleteGroupEventDTO deleteGroupEventDTO) {
        try {
            this.eventService.deleteGroup(deleteGroupEventDTO);
            return new ResponseEntity<>("Group deleted successfully", HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>("Group not found", HttpStatus.NOT_FOUND);
        }
    }

}
