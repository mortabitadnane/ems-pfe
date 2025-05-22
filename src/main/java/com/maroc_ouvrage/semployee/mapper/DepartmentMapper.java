package com.maroc_ouvrage.semployee.mapper;

import com.maroc_ouvrage.semployee.dto.DepartmentDTO;
import com.maroc_ouvrage.semployee.model.Department;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DepartmentMapper {

    // Method to convert Department to DepartmentDTO
    public DepartmentDTO toDto(Department department) {
        if (department == null) {
            return null;
        }
        DepartmentDTO departmentDTO = new DepartmentDTO();
        departmentDTO.setId(department.getId());
        departmentDTO.setName(department.getName());
        departmentDTO.setLocation(department.getLocation());
        // No need to set employees here if you don't need them
        return departmentDTO;
    }

    // Method to convert DepartmentDTO to Department
    public Department toEntity(DepartmentDTO departmentDTO) {
        if (departmentDTO == null) {
            return null;
        }
        Department department = new Department();
        department.setId(departmentDTO.getId());
        department.setName(departmentDTO.getName());
        department.setLocation(departmentDTO.getLocation());
        // No need to set employees here if you don't need them
        return department;
    }

    // Method to convert a list of Departments to a list of DepartmentDTOs
    public List<DepartmentDTO> toDtoList(List<Department> departments) {
        if (departments == null) {
            return null;
        }
        return departments.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}

