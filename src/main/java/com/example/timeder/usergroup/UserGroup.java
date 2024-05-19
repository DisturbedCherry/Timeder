package com.example.timeder.usergroup;

import com.example.timeder.user.User;
import com.example.timeder.group.Group;
import jakarta.persistence.*;

//@Entity
//@Table(name="users_groups")

public class UserGroup {
    @Id
    @ManyToOne
    @JoinTable(name="user_id")
    private User user;

    @Id
    @ManyToOne
    @JoinTable(name="group_id")
    private Group group;

}
