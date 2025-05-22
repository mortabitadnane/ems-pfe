package com.maroc_ouvrage.semployee.service;

import com.maroc_ouvrage.semployee.dto.DepartmentDTO;

import java.util.List;

public interface DepartmentService {

    DepartmentDTO createDepartment(DepartmentDTO departmentDTO);

    DepartmentDTO getDepartmentById(Long id);

    List<DepartmentDTO> getAllDepartments();

    void deleteDepartment(Long id);

    void assignEmployeeToDepartment(Long employeeId, Long departmentId);
}

