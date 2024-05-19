package com.example.timeder.errorreport;

import com.example.timeder.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class ErrorReportDTO {
    private User sender;
    ErrorReportStatus status;
    private LocalDate dateTime;
    String content;
}
