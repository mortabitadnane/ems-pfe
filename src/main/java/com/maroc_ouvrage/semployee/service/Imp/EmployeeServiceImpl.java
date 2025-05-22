package com.maroc_ouvrage.semployee.service.Imp;

import com.maroc_ouvrage.semployee.dto.EmployeecontractDTO;
import com.maroc_ouvrage.semployee.mapper.EmployeeMapper;
import com.maroc_ouvrage.semployee.model.Contract;
import com.maroc_ouvrage.semployee.model.Employee;
import com.maroc_ouvrage.semployee.repo.ContractRepository;
import com.maroc_ouvrage.semployee.repo.EmployeeRepository;
import com.maroc_ouvrage.semployee.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final ContractRepository contractRepository;
    private final EmployeeMapper employeeMapper;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository,
                               ContractRepository contractRepository,
                               EmployeeMapper employeeMapper) {
        this.employeeRepository = employeeRepository;
        this.contractRepository = contractRepository;
        this.employeeMapper = employeeMapper;
    }

    @Override
    public EmployeecontractDTO createEmployeeWithContract(EmployeecontractDTO employeecontractDTO) {
        // Create new Employee and Contract entities
        Employee employee = new Employee();
        Contract contract = new Contract();

        // Map DTO to Employee and Contract entities
        employeeMapper.toEmployeeEntity(employeecontractDTO, employee);
        employeeMapper.toContractEntity(employeecontractDTO, contract);

        // Set the relationship (if necessary)
        contract.setEmployee(employee);

        // Save both entities
        employeeRepository.save(employee);
        contractRepository.save(contract);

        // Return the DTO of the newly created Employee and Contract
        return employeeMapper.toDto(employee, contract);
    }

    @Override
    public EmployeecontractDTO updateEmployee(Long id, EmployeecontractDTO employeecontractDTO) {
        // Find the existing Employee and Contract by ID
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        Contract contract = contractRepository.findByEmployeeId(id)
                .orElseThrow(() -> new RuntimeException("Contract not found"));

        // Update the Employee and Contract entities using the DTO
        employeeMapper.toEmployeeEntity(employeecontractDTO, employee);
        employeeMapper.toContractEntity(employeecontractDTO, contract);

        // Save the updated entities back to the database
        employeeRepository.save(employee);
        contractRepository.save(contract);

        // Return the updated DTO
        return employeeMapper.toDto(employee, contract);
    }

    @Override
    public EmployeecontractDTO getEmployeeById(Long id) {
        // Fetch Employee and Contract from the database
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        Contract contract = contractRepository.findByEmployeeId(id)
                .orElseThrow(() -> new RuntimeException("Contract not found"));

        // Map the Employee and Contract entities to DTO and return
        return employeeMapper.toDto(employee, contract);
    }

    @Override
    public List<EmployeecontractDTO> getAllEmployees() {
        // Fetch all Employees and their Contracts
        List<Employee> employees = employeeRepository.findAll();

        // Map the list of Employee and Contract entities to a list of DTOs
        return employees.stream()
                .map(employee -> {
                    Contract contract = contractRepository.findByEmployeeId(employee.getId())
                            .orElseThrow(() -> new RuntimeException("Contract not found"));
                    return employeeMapper.toDto(employee, contract);
                })
                .collect(Collectors.toList());
    }

    @Override
    public void deleteEmployee(Long id) {
        // Find the Employee and Contract by ID
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        Contract contract = contractRepository.findByEmployeeId(id)
                .orElseThrow(() -> new RuntimeException("Contract not found"));

        // Delete the associated Contract and Employee from the database
        contractRepository.delete(contract);
        employeeRepository.delete(employee);
    }
}
