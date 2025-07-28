package com.maroc_ouvrage.semployee.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class DepartmentDTO {

        private Long departmentId;
        private String name;
        private String location;

}
