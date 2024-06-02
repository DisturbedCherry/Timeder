package com.example.timeder.usernotification;

import com.example.timeder.notification.Notification;
import com.example.timeder.user.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user_notifications")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserNotification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "notification_id")
    private Notification notification;

    public UserNotification(User user, Notification notification) {
        this.user = user;
        this.notification = notification;
    }

}