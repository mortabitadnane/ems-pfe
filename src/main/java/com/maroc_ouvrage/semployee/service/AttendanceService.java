package com.maroc_ouvrage.semployee.service;

import com.maroc_ouvrage.semployee.dto.AttendanceDTO;
import com.maroc_ouvrage.semployee.dto.AttendanceRequestDTO;
import com.maroc_ouvrage.semployee.dto.AttendanceStatsDTO;

import java.time.LocalDate;
import java.util.List;

public interface AttendanceService {
    AttendanceDTO createAttendance(AttendanceRequestDTO dto);
    List<AttendanceDTO> getAllAttendance();
    List<AttendanceDTO> getAttendanceByDate(LocalDate date);
    List<AttendanceDTO> getAttendanceByEmployee(Long employeeId);
    AttendanceStatsDTO getTodayStatistics();
}

