package com.maroc_ouvrage.semployee.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeecontractDTO {
    private Long userId;
    private Long employeeid;
    private String cin;
    private String firstname;
    private String lastname;
    private String position;
    private String rib;
    private Double salary;
    private String contractType;
    private LocalDate startDate;
    private LocalDate endDate;
}


