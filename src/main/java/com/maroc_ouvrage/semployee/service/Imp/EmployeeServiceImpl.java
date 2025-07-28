package com.maroc_ouvrage.semployee.service.Imp;

import com.maroc_ouvrage.semployee.dto.EmployeecontractDTO;
import com.maroc_ouvrage.semployee.mapper.EmployeeMapper;
import com.maroc_ouvrage.semployee.model.Contract;
import com.maroc_ouvrage.semployee.model.Employee;
import com.maroc_ouvrage.semployee.model.User;
import com.maroc_ouvrage.semployee.repo.ContractRepository;
import com.maroc_ouvrage.semployee.repo.EmployeeRepository;
import com.maroc_ouvrage.semployee.repo.UserRepository;
import com.maroc_ouvrage.semployee.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final ContractRepository contractRepository;
    private final EmployeeMapper employeeMapper;
    private final UserRepository userRepository;


    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository,
                               ContractRepository contractRepository,
                               EmployeeMapper employeeMapper,
                               UserRepository userRepository) {
        this.employeeRepository = employeeRepository;
        this.contractRepository = contractRepository;
        this.employeeMapper = employeeMapper;
        this.userRepository = userRepository;
    }

    @Override
    public EmployeecontractDTO createEmployeeWithContract(EmployeecontractDTO employeecontractDTO) {
        // Create new Employee and Contract entities
        Employee employee = new Employee();
        Contract contract = new Contract();

        // Map DTO to Employee and Contract entities
        employeeMapper.toEmployeeEntity(employeecontractDTO, employee);
        employeeMapper.toContractEntity(employeecontractDTO, contract);

        // Link user if userId present in DTO
        if (employeecontractDTO.getUserId() != null) {
            User user = userRepository.findById(employeecontractDTO.getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            employee.setUser(user);
        }else {
            // Optional: If no userId provided on update, remove user link (optional)
            employee.setUser(null);
        }

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

        if (employeecontractDTO.getUserId() != null) {
            User user = userRepository.findById(employeecontractDTO.getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            employee.setUser(user);
        }else {
            employee.setUser(null);
        }

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
    public EmployeecontractDTO getEmployeeByUsername(String username) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("Connected username: " + authentication.getName());

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> {
                    System.out.println("❌ User not found");
                    return new RuntimeException("User not found");
                });

        // Get the Employee linked to the User
        Employee employee = employeeRepository.findByUser(user)
                .orElseThrow(() -> {
                    System.out.println("❌ Employee not found");
                    return new RuntimeException("Employee not found for user");
                });

        // Get the Contract linked to the Employee
        Contract contract = contractRepository.findByEmployeeId(employee.getId())
                .orElseThrow(() -> {
                    System.out.println("❌ Contract not found");
                    return new RuntimeException("Contract not found for employee");
                });

        System.out.println("✅ Contract: " + contract.getId());

        return employeeMapper.toDto(employee,contract);
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

    public void unlinkUserFromEmployee(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        employee.setUser(null);
        employeeRepository.save(employee);
    }

    @Override
    public void deleteEmployee(Long id) {
        // Find the Employee and Contract by ID
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        Contract contract = contractRepository.findByEmployeeId(id)
                .orElseThrow(() -> new RuntimeException("Contract not found"));
        if (employee.getUser() != null) {
            throw new IllegalStateException("Cannot delete employee linked to a user. Unlink first.");
        }

        // Delete the associated Contract and Employee from the database
        contractRepository.delete(contract);
        employeeRepository.delete(employee);
    }
}
