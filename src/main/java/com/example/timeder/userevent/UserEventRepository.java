package com.example.timeder.userevent;

import com.example.timeder.event.Event;
import com.example.timeder.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserEventRepository extends JpaRepository<UserEvent, UserEventId> {

    Optional<UserEvent> findByUserAndEvent(User user, Event event);

}
