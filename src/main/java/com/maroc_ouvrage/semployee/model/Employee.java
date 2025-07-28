package com.maroc_ouvrage.semployee.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Employee {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(nullable = false, unique = true)
        private String cin;

        @Column(nullable = false)
        private String firstName;

        @Column(nullable = false)
        private String lastName;

        @Column(nullable = false)
        private String position;

        @Column(length = 24, nullable = false, unique = true)
        private String rib;


        @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
        private List<Conge> conges;

        @OneToOne(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
        private Contract contract;

        @ManyToOne(cascade = CascadeType.ALL)
        private Department department;

        @OneToOne
        @JoinColumn(name = "user_id")
        private User user;
        
}


