package com.example.timeder.groupevent;

import com.example.timeder.group.Group;
import com.example.timeder.event.Event;
import jakarta.persistence.*;

//@Entity
//@Table

public class GroupEvent {
    @Id
    @ManyToOne
    @JoinTable(name="group_id")
    private Group group;

    @Id
    @ManyToOne
    @JoinTable(name="event_id")
    private Event event;
}
