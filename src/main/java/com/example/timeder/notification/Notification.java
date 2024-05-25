package com.example.timeder.notification;

import com.example.timeder.user.User;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name = "notifications")
@Data
public class Notification {

    @Id
    @GeneratedValue
    private Integer id;

    private String content;
    private LocalDate dateTime;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Notification(String content, LocalDate dateTime, User user) {
        this.content = content;
        this.dateTime = dateTime;
        this.user = user;
    }

    public Notification() {
    }

}