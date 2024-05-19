package com.example.timeder.user;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name="users")
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

    public User(String firstName, String lastName, Integer index, String email, String password, UserStatus status) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.index = index;
        this.email = email;
        this.password = password;
        this.status = status;
    }

    public User() {}
}
