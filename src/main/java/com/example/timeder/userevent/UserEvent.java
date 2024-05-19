package com.example.timeder.userevent;

import com.example.timeder.user.User;
import com.example.timeder.event.Event;
import jakarta.persistence.*;

@Entity
@Table(name="users_events")

public class UserEvent {
    @Id
    @ManyToOne
    @JoinTable(name="user_id")
    private User user;

    @Id
    @ManyToOne
    @JoinTable(name="event_id")
    private Event event;
}