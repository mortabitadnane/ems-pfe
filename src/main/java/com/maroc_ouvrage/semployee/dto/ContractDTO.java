package com.maroc_ouvrage.semployee.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContractDTO {
    private String contractType;
    private LocalDate startDate;
    private LocalDate endDate;
    private Double salary;
    private String jobTitle;
    private String benefits;
}


