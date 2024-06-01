package com.example.timeder.usergroup;

import com.example.timeder.group.Group;
import com.example.timeder.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
public interface UserGroupRepository extends JpaRepository<UserGroup, UserGroupId> {
    Optional<UserGroup> findByUserAndGroup(User user, Group group);
}

