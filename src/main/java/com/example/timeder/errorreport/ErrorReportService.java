package com.example.timeder.errorreport;

import com.example.timeder.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ErrorReportService {
    private final ErrorReportRepository errorReportRepository;

    public ErrorReportService(ErrorReportRepository errorReportRepository) {
        this.errorReportRepository = errorReportRepository;
    }

    // CREATE

    public ErrorReport createErrorReport(ErrorReportDTO errorReportDTO) {
        ErrorReport newErrorReport = new ErrorReport(errorReportDTO.getStatus(), LocalDate.now(), errorReportDTO.getContent());
        this.errorReportRepository.save(newErrorReport);
        return newErrorReport;
    }

    // READ

    public List<ErrorReport> getErrorReports() {
        return this.errorReportRepository.findAll();
    }

    public ErrorReport getErrorReport(int id) throws ResourceNotFoundException {
        return this.errorReportRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Error report not found"));
    }

    // UPDATE

    public void updateErrorReport(int id, ErrorReportStatus errorReportStatus) throws ResourceNotFoundException {
        if (!this.errorReportRepository.existsById(id)) {
            throw new ResourceNotFoundException("Error report not found");
        }

        ErrorReport updatedErrorReport = this.errorReportRepository.getReferenceById(id);
        updatedErrorReport.setStatus(errorReportStatus);
        this.errorReportRepository.save(updatedErrorReport);
    }

    // REMOVE

    public void deleteErrorReport(int id) throws ResourceNotFoundException {
        if (!this.errorReportRepository.existsById(id)) {
            throw new ResourceNotFoundException("Error report not found");
        }

        this.errorReportRepository.deleteById(id);
    }

}
