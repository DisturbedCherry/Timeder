package com.example.timeder.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventDTO {

    private Integer id;
    private String name;
    private LocalDate startDateTime;
    private Boolean isPrivate;
    private String description;
    private String localization;
    private String photoFilePath;
    private Long ownerId;

}
