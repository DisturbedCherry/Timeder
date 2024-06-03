package com.example.timeder.event;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeleteUserEventDTO {

    private Integer eventId;
    private Long userIndex;

}
