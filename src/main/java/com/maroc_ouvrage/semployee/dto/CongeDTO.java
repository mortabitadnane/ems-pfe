package com.maroc_ouvrage.semployee.dto;
import com.maroc_ouvrage.semployee.model.CongeStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CongeDTO {
    private Long congeId;
    private LocalDate startDate;
    private LocalDate endDate;
    private CongeStatus status;
    private String reason;
    private String employeeCin;
}

