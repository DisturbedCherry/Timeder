package com.example.timeder.errorreport;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CreateErrorReportDTO {
    Integer index;
    String content;
}
