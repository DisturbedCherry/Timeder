package com.example.timeder.errorreport;

import com.example.timeder.exception.ResourceNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ErrorReportController {
    private final ErrorReportService errorReportService;

    public ErrorReportController(ErrorReportService errorReportService) {
        this.errorReportService = errorReportService;
    }

    @GetMapping("/")
    public List<ErrorReport> getErrorReports() {
        return this.errorReportService.getErrorReports();
    }

    @GetMapping("/{id}")
    public ErrorReport getErrorReport(@PathVariable int id) {
        try {
            return this.errorReportService.getErrorReport(id);
        } catch (ResourceNotFoundException e) {
            return null;
        }
    }

    // TODO Dodanie endpointa dla updatowania (tylko id i status)

    @PostMapping("/")
    public ErrorReport createErrorReport(@RequestBody ErrorReportDTO errorReportDTO) {
        return this.errorReportService.createErrorReport(errorReportDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteErrorReport(@PathVariable int id) {
        try {
            this.errorReportService.deleteErrorReport(id);
        } catch (Exception ignore) {
        }
    }
}
