package com.example.timeder.group;

import com.example.timeder.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GroupDTO {
    private String description;
    private Integer currentSize;
    private Integer totalSize;
    private Boolean isPrivate;
    private String joinCode;
    private User owner;
}
