package com.example.timeder.errorreport;

import com.example.timeder.exception.ResourceNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/error-reports")
public class ErrorReportController {

    private final ErrorReportService errorReportService;

    public ErrorReportController(ErrorReportService errorReportService) {
        this.errorReportService = errorReportService;
    }

    // CREATE

    @PostMapping("/")
    public ErrorReport createErrorReport(@RequestBody ErrorReportDTO errorReportDTO) {
        return this.errorReportService.createErrorReport(errorReportDTO);
    }

    // READ

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

    // UPDATE

    @PutMapping("/{id}")
    public ErrorReport updateErrorReport(@PathVariable int id, @RequestBody ErrorReportDTO errorReportDTO) {
        try {
            return this.errorReportService.updateErrorReport(id, errorReportDTO);
        } catch (ResourceNotFoundException e) {
            return null;
        }
    }

    // DELETE

    @DeleteMapping("/{id}")
    public void deleteErrorReport(@PathVariable int id) {
        try {
            this.errorReportService.deleteErrorReport(id);
        } catch (Exception ignore) {
        }
    }

}
