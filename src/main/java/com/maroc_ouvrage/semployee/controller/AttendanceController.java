package com.maroc_ouvrage.semployee.controller;

import com.maroc_ouvrage.semployee.dto.AttendanceDTO;
import com.maroc_ouvrage.semployee.dto.AttendanceRequestDTO;
import com.maroc_ouvrage.semployee.dto.AttendanceStatsDTO;
import com.maroc_ouvrage.semployee.service.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/attendance")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
@RequiredArgsConstructor
public class AttendanceController {

    private final AttendanceService attendanceService;

    // POST: Create new attendance
    @PostMapping
    public ResponseEntity<AttendanceDTO> create(@RequestBody AttendanceRequestDTO request) {
        AttendanceDTO dto = attendanceService.createAttendance(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    // GET: Get all attendance records
    @GetMapping
    public ResponseEntity<List<AttendanceDTO>> getAll() {
        return ResponseEntity.ok(attendanceService.getAllAttendance());
    }

    // GET: Get attendance for a specific date
    @GetMapping("/by-date")
    public ResponseEntity<List<AttendanceDTO>> getByDate(@RequestParam("date") LocalDate date) {
        return ResponseEntity.ok(attendanceService.getAttendanceByDate(date));
    }

    @GetMapping("/by-employee/{employeeId}")
    public ResponseEntity<List<AttendanceDTO>> getByEmployee(@PathVariable Long employeeId) {
        return ResponseEntity.ok(attendanceService.getAttendanceByEmployee(employeeId));
    }

    @GetMapping("/stats/daily")
    public ResponseEntity<AttendanceStatsDTO> getTodayStats() {
        return ResponseEntity.ok(attendanceService.getTodayStatistics());
    }


}

