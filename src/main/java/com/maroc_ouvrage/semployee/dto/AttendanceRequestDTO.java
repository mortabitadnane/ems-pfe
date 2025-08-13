package com.maroc_ouvrage.semployee.dto;

import com.maroc_ouvrage.semployee.model.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AttendanceRequestDTO {
    private Long employeeId;
    private LocalDate date;
    private Status status;
}

