package com.sridairy.erp.repository;

import com.sridairy.erp.model.DailyLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface DailyLogRepository extends JpaRepository<DailyLog, Long> {

    List<DailyLog> findByLogDate(LocalDate date);

    @Query("SELECT d.grade, SUM(d.quantity) FROM DailyLog d GROUP BY d.grade")
    List<Object[]> getGradeWiseTotals();
}