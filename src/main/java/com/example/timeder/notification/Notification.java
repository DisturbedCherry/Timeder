package com.example.timeder.notification;

import com.example.timeder.user.User;
import com.example.timeder.usernotification.UserNotification;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "notifications")
@Data
public class Notification {

    @Id
    @GeneratedValue
    private Integer id;

    private String content;
    private LocalDate dateTime;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @JsonManagedReference
    @OneToMany(mappedBy = "notification", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<UserNotification> userNotifications = Collections.emptyList();

    public Notification(String content, LocalDate dateTime) {
        this.content = content;
        this.dateTime = dateTime;
    }

    public Notification() {
    }

}