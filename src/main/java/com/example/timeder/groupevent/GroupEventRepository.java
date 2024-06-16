package com.example.timeder.groupevent;

import com.example.timeder.event.Event;
import com.example.timeder.group.Group;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GroupEventRepository extends JpaRepository<GroupEvent, GroupEventId> {

    Optional<GroupEvent> findByGroupAndEvent(Group group, Event event);

}
