package com.example.timeder.event;

import jakarta.persistence.*;
import com.example.timeder.user.User;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name="events")

@Data
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
    @JoinTable(name = "users")
    private User owner;

    public Event(String name, LocalDate startDateTime, Boolean isPrivate, String description, String localization, String photoFilePath) {
        this.name = name;
        this.startDateTime = startDateTime;
        this.isPrivate = isPrivate;
        this.description = description;
        this.localization = localization;
        this.photoFilePath = photoFilePath;
    }

    public Event() {}

}
