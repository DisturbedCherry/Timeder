package com.example.timeder.notification;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class NotificationDTO {
    private String content;
    private LocalDate dateTime;
}
