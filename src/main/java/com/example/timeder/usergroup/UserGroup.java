package com.example.timeder.usergroup;

import com.example.timeder.group.Group;
import com.example.timeder.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users_groups")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserGroup {

    @Id
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Id
    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;

}
