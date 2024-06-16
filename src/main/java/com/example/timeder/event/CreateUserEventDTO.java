package com.example.timeder.event;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateUserEventDTO {

    private Integer eventId;
    private Integer index;
    private String firstName;
    private String lastName;

}
