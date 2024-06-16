package com.example.timeder.group;


import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<Group, Integer> {
    boolean existsByJoinCode(String joinCode);
}
