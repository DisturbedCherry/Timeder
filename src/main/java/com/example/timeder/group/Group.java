package com.example.timeder.group;

import com.example.timeder.user.User;
import com.example.timeder.usergroup.UserGroup;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "groups")
@Data
@AllArgsConstructor
public class Group {

    @Id
    @GeneratedValue
    private Integer id;

    private String name;
    private String description;
    private Integer currentSize;
    private Integer totalSize;
    private Boolean isPrivate;
    private String joinCode;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User owner;

    // One-to-many relationship from Group to UserGroup
    // cascade -> will delete all associated UserGroup rows when Group is deleted
    @OneToMany(mappedBy = "group", fetch = FetchType.EAGER)
    private List<UserGroup> userGroups;

    public Group(String name, String description, Integer currentSize, Integer totalSize, Boolean isPrivate, String joinCode, User owner) {
        this.name = name;
        this.description = description;
        this.currentSize = currentSize;
        this.totalSize = totalSize;
        this.isPrivate = isPrivate;
        this.joinCode = joinCode;
        this.owner = owner;
        this.userGroups = new ArrayList<>();
    }

    public Group() {
    }

}
