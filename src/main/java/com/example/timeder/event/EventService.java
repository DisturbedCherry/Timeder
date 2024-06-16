package com.example.timeder.event;

import com.example.timeder.exception.ResourceNotFoundException;
import com.example.timeder.group.Group;
import com.example.timeder.group.GroupRepository;
import com.example.timeder.groupevent.GroupEvent;
import com.example.timeder.groupevent.GroupEventRepository;
import com.example.timeder.user.User;
import com.example.timeder.user.UserRepository;
import com.example.timeder.userevent.UserEvent;
import com.example.timeder.userevent.UserEventRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class EventService {

    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final GroupRepository groupRepository;
    private final UserEventRepository userEventRepository;
    private final GroupEventRepository groupEventRepository;

    public EventService(EventRepository eventRepository, UserRepository userRepository, GroupRepository groupRepository, UserEventRepository userEventRepository, GroupEventRepository groupEventRepository) {
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
        this.groupRepository = groupRepository;
        this.userEventRepository = userEventRepository;
        this.groupEventRepository = groupEventRepository;
    }

    @Transactional
    public EventDTO createEvent(EventDTO eventDTO) throws ResourceNotFoundException {
        Optional<User> user = this.userRepository.findById(Math.toIntExact(eventDTO.getOwnerId()));

        if (user.isEmpty()) {
            throw new ResourceNotFoundException("User not found");
        }

        Event newEvent = new Event(eventDTO.getName(), eventDTO.getStartDateTime(), eventDTO.getIsPrivate(), eventDTO.getDescription(), eventDTO.getLocalization(), eventDTO.getPhotoFilePath(), user.get());
        this.eventRepository.save(newEvent);

        UserEvent userEvent = new UserEvent(user.get(), newEvent);
        this.userEventRepository.save(userEvent);

        return mapToDTO(newEvent);
    }

    public List<EventDTO> getEvents() {
        List<Event> events = this.eventRepository.findAll();
        List<EventDTO> eventDTOs = new ArrayList<>();

        for (Event event : events) {
            eventDTOs.add(mapToDTO(event));
        }

        return eventDTOs;
    }

    public EventDTO getEvent(int id) throws ResourceNotFoundException {
        Optional<Event> eventOptional = this.eventRepository.findById(id);

        if (eventOptional.isEmpty()) {
            throw new ResourceNotFoundException("Event not found");
        }

        return mapToDTO(eventOptional.get());
    }

    @Transactional
    public List<UserEventDTO> getEventMembers(int id) throws ResourceNotFoundException {
        Optional<Event> eventOptional = this.eventRepository.findById(id);

        if (eventOptional.isEmpty()) {
            throw new ResourceNotFoundException("Event not found");
        }

        List<User> users = eventOptional.get().getUserEvents().stream().map(UserEvent::getUser).toList();

        List<UserEventDTO> userEventDTOs = new ArrayList<>();

        for (User user : users) {
            userEventDTOs.add(mapToUserEventDTO(user));
        }

        return userEventDTOs;
    }

    @Transactional
    public List<GroupEventDTO> getEventGroups(int id) throws ResourceNotFoundException {
        Optional<Event> eventOptional = this.eventRepository.findById(id);

        if (eventOptional.isEmpty()) {
            throw new ResourceNotFoundException("Event not found");
        }

        List<Group> groups = eventOptional.get().getGroupEvents().stream().map(GroupEvent::getGroup).toList();

        List<GroupEventDTO> groupEventDTOs = new ArrayList<>();

        for (Group group : groups) {
            groupEventDTOs.add(mapToGroupEventDTO(group));
        }

        return groupEventDTOs;
    }

    public EventDTO updateEvent(int id, EventDTO eventDTO) throws ResourceNotFoundException {
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

        return mapToDTO(this.eventRepository.save(updatedEvent));
    }

    @Transactional
    public UserEventDTO addUserToEvent(CreateUserEventDTO createUserEventDTO) throws ResourceNotFoundException {
        Optional<User> userOptional = this.userRepository.findByIndex(createUserEventDTO.getIndex());
        Optional<Event> eventOptional = this.eventRepository.findById(createUserEventDTO.getEventId());

        if (userOptional.isEmpty() || eventOptional.isEmpty()) {
            throw new ResourceNotFoundException("User or Event not found");
        }

        UserEvent userEvent = new UserEvent(userOptional.get(), eventOptional.get());

        Optional<UserEvent> existingUserEventOptional = this.userEventRepository.findByUserAndEvent(userOptional.get(), eventOptional.get());
        if (existingUserEventOptional.isPresent()) {
            throw new IllegalArgumentException("User is already a member of the event");
        }

        this.userEventRepository.save(userEvent);

        eventOptional.get().getUserEvents().add(userEvent);
        userOptional.get().getUserEvents().add(userEvent);

        return mapToUserEventDTO(userOptional.get());
    }

    @Transactional
    public GroupEventDTO addGroupToEvent(CreateGroupEventDTO createGroupEventDTO) throws ResourceNotFoundException {
        Optional<Group> groupOptional = this.groupRepository.findByName(createGroupEventDTO.getGroupName());
        Optional<Event> eventOptional = this.eventRepository.findById(createGroupEventDTO.getEventId());

        if (groupOptional.isEmpty() || eventOptional.isEmpty()) {
            throw new ResourceNotFoundException("Group or Event not found");
        }

        GroupEvent groupEvent = new GroupEvent(groupOptional.get(), eventOptional.get());

        Optional<GroupEvent> existingGroupEventOptional = this.groupEventRepository.findByGroupAndEvent(groupOptional.get(), eventOptional.get());
        if (existingGroupEventOptional.isPresent()) {
            throw new IllegalArgumentException("Group is already a member of the event");
        }

        this.groupEventRepository.save(groupEvent);

        eventOptional.get().getGroupEvents().add(groupEvent);
        groupOptional.get().getGroupEvents().add(groupEvent);

        return mapToGroupEventDTO(groupOptional.get());
    }

    public void deleteEvent(int id) throws ResourceNotFoundException {
        Optional<Event> eventOptional = this.eventRepository.findById(id);

        if (eventOptional.isEmpty()) {
            throw new ResourceNotFoundException("Event not found");
        }

        for (UserEvent userEvent : eventOptional.get().getUserEvents()) {
            if (Objects.equals(userEvent.getEvent().getId(), id)) {
                this.userEventRepository.delete(userEvent);
            }
        }

        for (GroupEvent groupEvent : eventOptional.get().getGroupEvents()) {
            if (Objects.equals(groupEvent.getEvent().getId(), id)) {
                this.groupEventRepository.delete(groupEvent);
            }
        }

        this.eventRepository.deleteById(id);
    }

    public void deleteMember(DeleteUserEventDTO deleteUserEventDTO) throws ResourceNotFoundException {
        Optional<Event> eventOptional = this.eventRepository.findById(deleteUserEventDTO.getEventId());

        if (eventOptional.isEmpty()) {
            throw new ResourceNotFoundException("Event not found");
        }

        Optional<User> userOptional = this.userRepository.findByIndex(Math.toIntExact(deleteUserEventDTO.getUserIndex()));
        if (userOptional.isEmpty()) {
            throw new ResourceNotFoundException("User not found");
        }

        // Check if the user to be removed is the owner of the group
        if (Objects.equals(eventOptional.get().getOwner().getId(), userOptional.get().getId())) {
            throw new IllegalArgumentException("Cannot remove the owner of the event");
        }

        List<UserEvent> userEvents = eventOptional.get().getUserEvents();

        for (UserEvent userEvent : userEvents) {
            if (Objects.equals(userEvent.getUser().getId(), userOptional.get().getId())) {
                this.userEventRepository.delete(userEvent);
            }
        }
    }

    public void deleteGroup(DeleteGroupEventDTO deleteGroupEventDTO) throws ResourceNotFoundException {
        Optional<Event> eventOptional = this.eventRepository.findById(deleteGroupEventDTO.getEventId());

        if (eventOptional.isEmpty()) {
            throw new ResourceNotFoundException("Event not found");
        }

        Optional<Group> groupOptional = this.groupRepository.findByName(deleteGroupEventDTO.getGroupName());
        if (groupOptional.isEmpty()) {
            throw new ResourceNotFoundException("Group not found");
        }

        List<GroupEvent> groupEvents = eventOptional.get().getGroupEvents();

        for (GroupEvent groupEvent : groupEvents) {
            if (Objects.equals(groupEvent.getGroup().getId(), groupOptional.get().getId())) {
                this.groupEventRepository.delete(groupEvent);
            }
        }
    }

    private EventDTO mapToDTO(Event event) {
        EventDTO eventDTO = new EventDTO();
        eventDTO.setId(event.getId());
        eventDTO.setName(event.getName());
        eventDTO.setStartDateTime(event.getStartDateTime());
        eventDTO.setIsPrivate(event.getIsPrivate());
        eventDTO.setDescription(event.getDescription());
        eventDTO.setLocalization(event.getLocalization());
        eventDTO.setPhotoFilePath(event.getPhotoFilePath());
        eventDTO.setOwnerId(event.getOwner().getId());
        return eventDTO;
    }

    private UserEventDTO mapToUserEventDTO(User user) {
        UserEventDTO userEventDTO = new UserEventDTO();
        userEventDTO.setFirstName(user.getFirstName());
        userEventDTO.setLastName(user.getLastName());
        userEventDTO.setIndex(user.getIndex());
        return userEventDTO;
    }

    private GroupEventDTO mapToGroupEventDTO(Group group) {
        GroupEventDTO groupEventDTO = new GroupEventDTO();
        groupEventDTO.setName(group.getName());
        groupEventDTO.setId(group.getId());
        return groupEventDTO;
    }

}
