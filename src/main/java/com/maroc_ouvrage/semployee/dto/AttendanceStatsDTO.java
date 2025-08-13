package com.maroc_ouvrage.semployee.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AttendanceStatsDTO {
    private long total;
    private long present;
    private long absent;
    private long onLeave;
    private long late;
}

