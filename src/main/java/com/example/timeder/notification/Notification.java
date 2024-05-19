package com.example.timeder.notification;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name = "notifications")
@Data
public class Notification {

    @Id
    @GeneratedValue
    private Integer id;

    private String content;
    private LocalDate dateTime;

    public Notification(String content, LocalDate dateTime) {
        this.content = content;
        this.dateTime = dateTime;
    }

    public Notification() {
    }

}
