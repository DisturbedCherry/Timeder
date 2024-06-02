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

    public ErrorReportDTO createErrorReport(CreateErrorReportDTO errorReportDTO) throws ResourceNotFoundException {
        Optional<User> senderOptional = userRepository.findByIndex(errorReportDTO.getIndex());

        if(senderOptional.isEmpty()) {
            throw new ResourceNotFoundException("Sender not found");
        }

        ErrorReport newErrorReport = new ErrorReport(ErrorReportStatus.PENDING, LocalDate.now(), errorReportDTO.getContent(), senderOptional.get());
        newErrorReport = errorReportRepository.save(newErrorReport);
        return mapToDTO(newErrorReport);
    }

    public List<ErrorReportDTO> getErrorReports() {
        List<ErrorReportDTO> errorReportDTOs = new ArrayList<>();

        List<ErrorReport> errorReports = errorReportRepository.findAll();

        for(ErrorReport errorReport : errorReports) {
            errorReportDTOs.add(mapToDTO(errorReport));
        }

        return errorReportDTOs;
    }

    public ErrorReportDTO getErrorReport(int id) throws ResourceNotFoundException {
        Optional<ErrorReport> errorReportOptional = errorReportRepository.findById(id);

        if(errorReportOptional.isEmpty()) {
            throw new ResourceNotFoundException("Error report not found");
        }

        return mapToDTO(errorReportOptional.get());
    }

    public ErrorReportDTO updateErrorReport(UpdateErrorReportDTO updateErrorReportDTO) throws ResourceNotFoundException {
        Optional<ErrorReport> errorReportOptional = errorReportRepository.findById(updateErrorReportDTO.getId());

        if(errorReportOptional.isEmpty()) {
            throw new ResourceNotFoundException("Error report not found");
        }

        ErrorReport errorReport = errorReportOptional.get();

        if (updateErrorReportDTO.getStatus() != null) {
            errorReport.setStatus(ErrorReportStatus.valueOf(updateErrorReportDTO.getStatus()));
            errorReportRepository.save(errorReport);
        }

        return mapToDTO(errorReportRepository.save(errorReport));
    }

    public void deleteErrorReport(int id) throws ResourceNotFoundException {
        if (!errorReportRepository.existsById(id)) {
            throw new ResourceNotFoundException("Error report not found");
        }

        errorReportRepository.deleteById(id);
    }

    private ErrorReportDTO mapToDTO(ErrorReport errorReport) {
        Integer id = errorReport.getId();
        Integer senderIndex = errorReport.getSender().getIndex();
        String content = errorReport.getContent();
        String status = String.valueOf(errorReport.getStatus());
        LocalDate date = errorReport.getDateTime();

        return new ErrorReportDTO(id, senderIndex, content, status, date);
    }
}
