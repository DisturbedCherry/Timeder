package com.example.timeder.userevent;

import com.example.timeder.user.User;
import com.example.timeder.event.Event;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name="users_events")
@Data

public class UserEvent {
    @Id
    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @Id
    @ManyToOne
    @JoinColumn(name="event_id")
    private Event event;
}