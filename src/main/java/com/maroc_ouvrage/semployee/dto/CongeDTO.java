package com.maroc_ouvrage.semployee.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CongeDTO {
    private LocalDate startDate;
    private LocalDate endDate;
    private String status;
    private String reason;
    private String employeeCin;
}

