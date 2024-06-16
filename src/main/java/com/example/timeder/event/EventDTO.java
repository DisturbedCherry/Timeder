package com.example.timeder.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventDTO {

    private Integer id;
    private String name;
    private LocalDateTime startDateTime;
    private Boolean isPrivate;
    private String description;
    private String localization;
    private String photoFilePath;
    private Long ownerId;

}
