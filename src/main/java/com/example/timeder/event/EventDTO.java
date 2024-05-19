package com.example.timeder.event;

import lombok.Data;

import java.time.LocalDate;

@Data
public class EventDTO {
    private String name;
    private LocalDate startDateTime;
    private Boolean isPrivate;
    private String description;
    private String localization;
    private String photoFilePath;
}
