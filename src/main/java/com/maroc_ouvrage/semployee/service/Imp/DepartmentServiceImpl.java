package com.maroc_ouvrage.semployee.service.Imp;

import com.maroc_ouvrage.semployee.audit.Auditable;
import com.maroc_ouvrage.semployee.dto.DepartmentDTO;
import com.maroc_ouvrage.semployee.mapper.DepartmentMapper;
import com.maroc_ouvrage.semployee.model.*;
import com.maroc_ouvrage.semployee.repo.DepartmentRepository;
import com.maroc_ouvrage.semployee.repo.EmployeeRepository;
import com.maroc_ouvrage.semployee.service.DepartmentService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private DepartmentMapper departmentMapper;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Auditable(action = "DEPARTMENT_CREATED", details = "create a department successfully")
    @Override
    public DepartmentDTO createDepartment(DepartmentDTO departmentDTO) {
        System.out.println("Mapping departmentDTO: " + departmentDTO);
        Department department = departmentMapper.toEntity(departmentDTO);
        department = departmentRepository.save(department);
        return departmentMapper.toDto(department);
    }

    @Auditable(action = "DEPARTMENT_UPDATED", details = "update a department successfully")
    @Override
    public DepartmentDTO updateDepartment(Long id, DepartmentDTO departmentDTO) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Department not found"));

        departmentMapper.updateEntityFromDTO(departmentDTO, department); // update existing entity

        department = departmentRepository.save(department); // save updated entity


        return departmentMapper.toDto(department); // return updated DTO
    }


    @Override
    public DepartmentDTO getDepartmentById(Long id) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Department not found"));
        return departmentMapper.toDto(department);
    }

    @Override
    public List<DepartmentDTO> getAllDepartments() {
        List<Department> departments = departmentRepository.findAll();
        return departmentMapper.toDtoList(departments);
    }

    @Auditable(action = "EMPLOYEE_ASSIGNED", details = "assign an employee to department successfully")
    @Override
    public void assignEmployeeToDepartment(Long employeeId, Long departmentId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found"));
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new EntityNotFoundException("Department not found"));

        employee.setDepartment(department);
        employeeRepository.save(employee);
        System.out.println("Assigned successfully: Employee " + employeeId + " to Department " + departmentId);

    }

    @Auditable(action = "DEPARTMENT_DELETED", details = "delete a department successfully")
    @Override
    public void deleteDepartment(Long departmentId) {
        departmentRepository.deleteById(departmentId);
    }

}

