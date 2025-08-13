package com.maroc_ouvrage.semployee.repo;

import com.maroc_ouvrage.semployee.model.Attendance;
import com.maroc_ouvrage.semployee.model.Employee;
import com.maroc_ouvrage.semployee.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    // Example custom queries you might want to add:

    // Find attendance by employee and date
    Optional<Attendance> findByEmployeeAndDate(Employee employee, LocalDate date);

    // Find all attendances for a specific day
    List<Attendance> findAllByDate(LocalDate date);
    
    List<Attendance> findByEmployeeId(Long employeeid);

    List<Attendance> findByDate(LocalDate date);


    // Count present/absent for a day
    long countByDateAndStatus(LocalDate date, Status status);

    // All attendance for one employee
    List<Attendance> findByEmployee(Employee employee);
}

