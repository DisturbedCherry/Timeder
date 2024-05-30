package com.example.timeder.usergroup;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
public class UserGroupKey implements Serializable {
    private Long userId;
    private Integer groupId;

}