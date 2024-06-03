package com.example.timeder.event;

import com.example.timeder.groupevent.GroupEvent;
import com.example.timeder.user.User;
import com.example.timeder.userevent.UserEvent;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "events")
@Data
@AllArgsConstructor
public class Event {

    @Id
    @GeneratedValue
    private Integer id;

    private String name;
    private LocalDate startDateTime;
    private Boolean isPrivate;
    private String description;
    private String localization;
    private String photoFilePath;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User owner;

    // One-to-many relationship from Event to UserEvent
    // cascade -> will delete all associated UserEvent rows when Event is deleted
    @OneToMany(mappedBy = "event", fetch = FetchType.EAGER)
    private List<UserEvent> userEvents;

    // One-to-many relationship from Event to GroupEvent
    // cascade -> will delete all associated GroupEvent rows when Event is deleted
    @OneToMany(mappedBy = "event", fetch = FetchType.EAGER)
    private List<GroupEvent> groupEvents;

    public Event(String name, LocalDate startDateTime, Boolean isPrivate, String description, String localization, String photoFilePath, User owner) {
        this.name = name;
        this.startDateTime = startDateTime;
        this.isPrivate = isPrivate;
        this.description = description;
        this.localization = localization;
        this.photoFilePath = photoFilePath;
        this.owner = owner;
        this.userEvents = new ArrayList<>();
        this.groupEvents = new ArrayList<>();
    }

    public Event() {
    }

}
