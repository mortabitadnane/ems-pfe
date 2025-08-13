package com.maroc_ouvrage.semployee.service.Imp;

import com.maroc_ouvrage.semployee.dto.AttendanceDTO;
import com.maroc_ouvrage.semployee.dto.AttendanceRequestDTO;
import com.maroc_ouvrage.semployee.dto.AttendanceStatsDTO;
import com.maroc_ouvrage.semployee.mapper.AttendanceMapper;
import com.maroc_ouvrage.semployee.model.Attendance;
import com.maroc_ouvrage.semployee.model.Employee;
import com.maroc_ouvrage.semployee.model.Status;
import com.maroc_ouvrage.semployee.repo.AttendanceRepository;
import com.maroc_ouvrage.semployee.repo.EmployeeRepository;
import com.maroc_ouvrage.semployee.service.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AttendanceServiceImpl implements AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final EmployeeRepository employeeRepository;
    private final AttendanceMapper attendanceMapper;

    @Override
    public AttendanceDTO createAttendance(AttendanceRequestDTO dto) {
        Attendance attendance = attendanceMapper.toEntity(dto);

        Employee employee = employeeRepository.findById(dto.getEmployeeId())
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        attendance.setEmployee(employee);
        Attendance saved = attendanceRepository.save(attendance);

        return attendanceMapper.toDTO(saved);
    }

    @Override
    public List<AttendanceDTO> getAllAttendance() {
        return attendanceMapper.toDTOs(attendanceRepository.findAll());
    }

    @Override
    public List<AttendanceDTO> getAttendanceByDate(LocalDate date) {
        return attendanceMapper.toDTOs(attendanceRepository.findAllByDate(date));
    }

    @Override
    public List<AttendanceDTO> getAttendanceByEmployee(Long employeeid) {
        List<Attendance> attendances = attendanceRepository.findByEmployeeId(employeeid);
        return attendances.stream()
                .map(attendanceMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public AttendanceStatsDTO getTodayStatistics() {
        LocalDate today = LocalDate.now();
        List<Attendance> records = attendanceRepository.findByDate(today);

        AttendanceStatsDTO stats = new AttendanceStatsDTO();
        stats.setTotal(records.size());
        stats.setPresent(records.stream().filter(a -> a.getStatus() == Status.PRESENT).count());
        stats.setAbsent(records.stream().filter(a -> a.getStatus() == Status.ABSENT).count());
        stats.setOnLeave(records.stream().filter(a -> a.getStatus() == Status.ON_LEAVE).count());
        stats.setLate(records.stream().filter(a -> a.getStatus() == Status.LATE).count());

        return stats;
    }

}

