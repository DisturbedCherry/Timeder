package com.example.timeder.errorreport;

import com.example.timeder.user.User;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name="error_reports")

@Data
public class ErrorReport {
    @Id
    @GeneratedValue
    private Integer id;

    @Id
    @ManyToOne
    @JoinTable(name = "users")
    private User sender;

    private ErrorReportStatus status;
    private LocalDate dateTime;
    private String content;

    public ErrorReport(ErrorReportStatus status, LocalDate dateTime, String content) {
        this.status = status;
        this.dateTime = dateTime;
        this.content = content;
    }

    public ErrorReport() {}
}
