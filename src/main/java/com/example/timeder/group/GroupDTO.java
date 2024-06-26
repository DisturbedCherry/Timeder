package com.example.timeder.group;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupDTO {

    private Integer id;
    private String name;
    private String description;
    private Integer currentSize;
    private Integer totalSize;
    private Boolean isPrivate;
    private String joinCode;
    private Long ownerId;

}
