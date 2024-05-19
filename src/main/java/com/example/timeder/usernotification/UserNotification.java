package com.example.timeder.usernotification;


import com.example.timeder.notification.Notification;
import com.example.timeder.user.User;
import jakarta.persistence.*;

@Entity
@Table(name="users_notification")

public class UserNotification {
    @Id
    @ManyToOne
    @JoinTable(name = "user_id")
    private User user;

    @Id
    @ManyToOne
    @JoinTable(name = "notification_id")
    private Notification notification;

    private Integer creator;
}
