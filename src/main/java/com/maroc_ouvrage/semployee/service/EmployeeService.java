package com.maroc_ouvrage.semployee.service;

import com.maroc_ouvrage.semployee.dto.EmployeecontractDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface EmployeeService {
    EmployeecontractDTO createEmployeeWithContract(EmployeecontractDTO employeecontractDTO);
    EmployeecontractDTO updateEmployee(Long id, EmployeecontractDTO employeecontractDTO);
    EmployeecontractDTO getEmployeeById(Long id);
    EmployeecontractDTO getEmployeeByUsername(String username);
    List<EmployeecontractDTO> getAllEmployees();
    void unlinkUserFromEmployee(Long employeeId);
    void deleteEmployee(Long id);
    void uploadEmployeeImage(Long employeeId, MultipartFile imageFile) throws IOException;
    void uploadProfileImageForAuthenticatedUser(MultipartFile imageFile) throws IOException;

}
