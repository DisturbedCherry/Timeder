package com.example.timeder.errorreport;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;


@AllArgsConstructor
@Getter
@Setter
public class ErrorReportDTO {
    Integer id;
    Integer userId;
    String content;
    String status;
    LocalDate dateTime;
}
