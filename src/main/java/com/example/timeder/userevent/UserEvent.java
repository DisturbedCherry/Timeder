package com.example.timeder.userevent;

import com.example.timeder.event.Event;
import com.example.timeder.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users_events")
@IdClass(UserEventId.class)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserEvent {

    @Id
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Id
    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

}
