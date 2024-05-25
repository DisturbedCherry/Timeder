package com.example.timeder.errorreport;

import com.example.timeder.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/error-reports")
public class ErrorReportController {

    private final ErrorReportService errorReportService;

    public ErrorReportController(ErrorReportService errorReportService) {
        this.errorReportService = errorReportService;
    }

    @PostMapping("/")
    public ResponseEntity<ErrorReportDTO> createErrorReport(@RequestBody CreateErrorReportDTO errorReportDTO) {
        try {
            ErrorReportDTO createdErrorReport = errorReportService.createErrorReport(errorReportDTO);
            return new ResponseEntity<>(createdErrorReport, HttpStatus.CREATED);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/")
    public ResponseEntity<List<ErrorReportDTO>> getErrorReports() {
        List<ErrorReportDTO> errorReports = errorReportService.getErrorReports();
        return new ResponseEntity<>(errorReports, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ErrorReportDTO> getErrorReport(@PathVariable int id) {
        try {
            ErrorReportDTO errorReport = errorReportService.getErrorReport(id);
            return new ResponseEntity<>(errorReport, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ErrorReportDTO> updateErrorReport(@RequestBody ErrorReportDTO errorReportDTO) {
        try {
            ErrorReportDTO updatedErrorReport = errorReportService.updateErrorReport(errorReportDTO);
            return new ResponseEntity<>(updatedErrorReport, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteErrorReport(@PathVariable int id) {
        try {
            errorReportService.deleteErrorReport(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}