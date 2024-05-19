package com.example.timeder.groupevent;

import com.example.timeder.group.Group;
import com.example.timeder.event.Event;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table
@Data
public class GroupEvent {
    @Id
    @ManyToOne
    @JoinColumn(name="group_id")
    private Group group;

    @Id
    @ManyToOne
    @JoinColumn(name="event_id")
    private Event event;
}
