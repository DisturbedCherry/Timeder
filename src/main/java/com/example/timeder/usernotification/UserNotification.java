package com.example.timeder.usernotification;


import com.example.timeder.notification.Notification;
import com.example.timeder.user.User;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "users_notification")
@Data
public class UserNotification {

    @Id
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Id
    @ManyToOne
    @JoinColumn(name = "notification_id")
    private Notification notification;

    // TODO Zrobić z tego relację między użytkownikiem a powiadomieniem
    private Integer creator;

}
