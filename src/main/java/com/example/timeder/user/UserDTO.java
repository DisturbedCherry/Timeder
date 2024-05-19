package com.example.timeder.user;

import lombok.Data;

@Data
public class UserDTO {
    private String firstName;
    private String lastName;
    private Integer index;
    private String email;
    private String password;
    private UserStatus status;
}
