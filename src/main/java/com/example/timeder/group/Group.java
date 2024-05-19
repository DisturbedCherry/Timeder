package com.example.timeder.group;

import com.example.timeder.user.User;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "groups")

@Data
public class Group {

    @Id
    @GeneratedValue
    private Integer id;

    private String description;
    private Integer currentSize;
    private Integer totalSize;
    private Boolean isPrivate;
    private String joinCode;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User owner;

    public Group(String description, Integer currentSize, Integer totalSize, Boolean isPrivate, String joinCode, User owner) {
        this.description = description;
        this.currentSize = currentSize;
        this.totalSize = totalSize;
        this.isPrivate = isPrivate;
        this.joinCode = joinCode;
        this.owner = owner;
    }

    public Group() {
    }

}
