package com.example.timeder.user;

import com.example.timeder.notification.Notification;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "users")
@Data
public class User {

    @Id
    @GeneratedValue
    private Integer id;

    private String firstName;
    private String lastName;
    private Integer index;
    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    private UserStatus status;

    // mappedBy -> indicates that user is not owning side of relationship
    // cascade -> any operation performed on User will be cascaded to associated Notification entities
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Notification> notifications;

    public User(String firstName, String lastName, Integer index, String email, String password, UserStatus status) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.index = index;
        this.email = email;
        this.password = password;
        this.status = status;
    }

    public User() {
    }

}