package com.maroc_ouvrage.semployee.service;

import com.maroc_ouvrage.semployee.dto.EmployeecontractDTO;

import java.util.List;

public interface EmployeeService {
    EmployeecontractDTO createEmployeeWithContract(EmployeecontractDTO employeecontractDTO);
    EmployeecontractDTO updateEmployee(Long id, EmployeecontractDTO employeecontractDTO);
    EmployeecontractDTO getEmployeeById(Long id);
    EmployeecontractDTO getEmployeeByUsername(String username);
    List<EmployeecontractDTO> getAllEmployees();
    void unlinkUserFromEmployee(Long employeeId);
    void deleteEmployee(Long id);
}
