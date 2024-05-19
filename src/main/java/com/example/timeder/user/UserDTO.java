package com.example.timeder.user;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDTO {

    private String firstName;
    private String lastName;
    private Integer index;
    private String email;
    private String password;
    private UserStatus status;

}
