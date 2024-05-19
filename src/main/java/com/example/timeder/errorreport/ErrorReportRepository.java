package com.example.timeder.errorreport;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ErrorReportRepository extends JpaRepository<ErrorReport, Integer> {
}
