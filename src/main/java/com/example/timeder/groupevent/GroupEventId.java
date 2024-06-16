package com.example.timeder.groupevent;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class GroupEventId implements Serializable {

    private Integer group;
    private Integer event;

}
