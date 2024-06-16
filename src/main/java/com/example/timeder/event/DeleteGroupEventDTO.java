package com.example.timeder.event;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeleteGroupEventDTO {

    private Integer eventId;
    private String groupName;

}
