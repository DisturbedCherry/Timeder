package com.example.timeder.event;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateGroupEventDTO {

    private Integer eventId;
    private Integer groupId;
    private String groupName;

}
