package com.example.timeder.notification;

import lombok.Data;

import java.time.LocalDate;

@Data
public class NotificationDTO {
    private String content;
    private LocalDate dateTime;
}
