package com.example.timeder.errorreport;

import com.example.timeder.exception.ResourceNotFoundException;
import com.example.timeder.user.User;
import com.example.timeder.user.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ErrorReportService {

    private final ErrorReportRepository errorReportRepository;
    private final UserRepository userRepository;

    public ErrorReportService(ErrorReportRepository errorReportRepository, UserRepository userRepository) {
        this.errorReportRepository = errorReportRepository;
        this.userRepository = userRepository;
    }

    // CREATE

    public ErrorReportDTO createErrorReport(CreateErrorReportDTO errorReportDTO) throws ResourceNotFoundException {
        Optional<User> senderOptional = userRepository.findById(errorReportDTO.getUserId());

        if(senderOptional.isEmpty()) {
            throw new ResourceNotFoundException("Sender not found");
        }

        ErrorReport newErrorReport = new ErrorReport(ErrorReportStatus.PENDING, LocalDate.now(), errorReportDTO.getContent(), senderOptional.get());
        newErrorReport = errorReportRepository.save(newErrorReport);
        return mapToDTO(newErrorReport);
    }

    // READ

    public List<ErrorReportDTO> getErrorReports() {
        List<ErrorReportDTO> errorReportsDTOs = new ArrayList<>();

        List<ErrorReport> errorReports = errorReportRepository.findAll();

        for(ErrorReport errorReport : errorReports) {
            errorReportsDTOs.add(mapToDTO(errorReport));
        }

        return errorReportsDTOs;
    }

    public ErrorReportDTO getErrorReport(int id) throws ResourceNotFoundException {
        Optional<ErrorReport> errorReportOptional = errorReportRepository.findById(id);

        if(errorReportOptional.isEmpty()) {
            throw new ResourceNotFoundException("Error report not found");
        }

        return mapToDTO(errorReportOptional.get());
    }

    // UPDATE

    public ErrorReportDTO updateErrorReport(ErrorReportDTO errorReportDTO) throws ResourceNotFoundException {
        Optional<ErrorReport> errorReportOptional = errorReportRepository.findById(errorReportDTO.getId());

        if(errorReportOptional.isEmpty()) {
            throw new ResourceNotFoundException("Error report not found");
        }

        ErrorReport errorReport = errorReportOptional.get();

        if (errorReportDTO.getStatus() != null) {
            errorReport.setStatus(ErrorReportStatus.valueOf(errorReportDTO.getStatus()));
        }
        if (errorReportDTO.getContent() != null) {
            errorReport.setContent(errorReportDTO.getContent());
        }
        if (errorReportDTO.getDateTime() != null) {
            errorReport.setDateTime(errorReportDTO.getDateTime());
        }

        return mapToDTO(errorReportRepository.save(errorReport));
    }

    // DELETE

    public void deleteErrorReport(int id) throws ResourceNotFoundException {
        if (!errorReportRepository.existsById(id)) {
            throw new ResourceNotFoundException("Error report not found");
        }

        errorReportRepository.deleteById(id);
    }


    private ErrorReportDTO mapToDTO(ErrorReport errorReport) {
        Integer id = errorReport.getId();
        Integer senderId = errorReport.getSender().getId();
        String content = errorReport.getContent();
        String status = String.valueOf(errorReport.getStatus());
        LocalDate date = errorReport.getDateTime();

        return new ErrorReportDTO(id, senderId, content, status, date);
    }
}
