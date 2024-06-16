package com.example.timeder.userevent;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class UserEventId implements Serializable {

    private Long user;
    private Integer event;

}
