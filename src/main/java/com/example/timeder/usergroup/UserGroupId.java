package com.example.timeder.usergroup;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class UserGroupId implements Serializable {
    private Long user;
    private Integer group;
}