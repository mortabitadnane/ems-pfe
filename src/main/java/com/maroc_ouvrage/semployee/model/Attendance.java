package com.maroc_ouvrage.semployee.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Attendance {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Employee employee;

    private LocalDate date;

    @Enumerated(EnumType.STRING)
    private Status status; // PRESENT, ABSENT, LATE, etc.
}
