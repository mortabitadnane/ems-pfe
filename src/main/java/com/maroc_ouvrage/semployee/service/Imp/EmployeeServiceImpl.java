package com.maroc_ouvrage.semployee.service.Imp;

import com.maroc_ouvrage.semployee.audit.Auditable;
import com.maroc_ouvrage.semployee.dto.EmployeecontractDTO;
import com.maroc_ouvrage.semployee.mapper.EmployeeMapper;
import com.maroc_ouvrage.semployee.model.*;
import com.maroc_ouvrage.semployee.repo.ContractRepository;
import com.maroc_ouvrage.semployee.repo.EmployeeRepository;
import com.maroc_ouvrage.semployee.repo.NotificationRepository;
import com.maroc_ouvrage.semployee.repo.UserRepository;
import com.maroc_ouvrage.semployee.service.EmployeeService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final ContractRepository contractRepository;
    private final EmployeeMapper employeeMapper;
    private final UserRepository userRepository;
    private final NotificationRepository notificationRepository;


    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository,
                               ContractRepository contractRepository,
                               EmployeeMapper employeeMapper,
                               UserRepository userRepository, NotificationRepository notificationRepository) {
        this.employeeRepository = employeeRepository;
        this.contractRepository = contractRepository;
        this.employeeMapper = employeeMapper;
        this.userRepository = userRepository;
        this.notificationRepository = notificationRepository;
    }
    private User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }

        String username = authentication.getName();
        return userRepository.findByUsername(username).orElse(null);
    }

    @Auditable(action = "EMPLOYEE_CREATED", details = "create an employee successfully")
    @Transactional
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
            user.setEmployee(employee);
        }else {
            // Optional: If no userId provided on update, remove user link (optional)
            employee.setUser(null);
        }

        // Set the relationship (if necessary)
        contract.setEmployee(employee);

        // Save both entities
        employeeRepository.save(employee);
        contractRepository.save(contract);

        User authenticatedUser = getAuthenticatedUser();
        User affectedUser = employee.getUser();

        if (affectedUser != null) {
            Notification notif = Notification.builder()
                    .recipient(affectedUser)
                    .title("Your Employee Data Was Created")
                    .message("Your employee information has been created successfully.")
                    .type(NotificationType.LEAVE_REQUEST)
                    .isRead(false)
                    .createdAt(LocalDateTime.now())
                    .build();
            notificationRepository.save(notif);
        }

        if (authenticatedUser != null && !authenticatedUser.equals(affectedUser)) {
            Notification notif = Notification.builder()
                    .recipient(authenticatedUser)
                    .title("New Employee Created")
                    .message("You successfully create a new employee : " + employee.getFirstName() + " " + employee.getLastName())
                    .type(NotificationType.LEAVE_REQUEST)
                    .isRead(false)
                    .createdAt(LocalDateTime.now())
                    .build();
            notificationRepository.save(notif);
        }
        // Return the DTO of the newly created Employee and Contract
        return employeeMapper.toDto(employee, contract);
    }

    @Auditable(action = "EMPLOYEE_UPDATED", details = "update an employee successfully")
    @Transactional
    @Override
    public EmployeecontractDTO updateEmployee(Long id, EmployeecontractDTO employeecontractDTO) {
        // Find the existing Employee and Contract by ID
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        Contract contract = contractRepository.findByEmployeeId(id)
                .orElseThrow(() -> new RuntimeException("Contract not found"));
        User oldUser = employee.getUser();

        // Update the Employee and Contract entities using the DTO
        employeeMapper.toEmployeeEntity(employeecontractDTO, employee);
        employeeMapper.toContractEntity(employeecontractDTO, contract);

        if (employeecontractDTO.getUserId() != null) {
            User user = userRepository.findById(employeecontractDTO.getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            employee.setUser(user);
            user.setEmployee(employee);
        }else {
            employee.setUser(null);
        }

        // Save the updated entities back to the database
        employeeRepository.save(employee);
        contractRepository.save(contract);

        User authenticatedUser = getAuthenticatedUser();
        User affectedUser = employee.getUser();

        if (affectedUser != null) {
            Notification notif = Notification.builder()
                    .recipient(affectedUser)
                    .title("Your Employee Data Was Updated")
                    .message("Your employee information has been updated successfully.")
                    .type(NotificationType.LEAVE_REQUEST)
                    .isRead(false)
                    .createdAt(LocalDateTime.now())
                    .build();
            notificationRepository.save(notif);
        }

        if (authenticatedUser != null && !authenticatedUser.equals(affectedUser)) {
            Notification notif = Notification.builder()
                    .recipient(authenticatedUser)
                    .title("Employee Updated")
                    .message("You successfully updated employee info for: " + employee.getFirstName() + " " + employee.getLastName())
                    .type(NotificationType.LEAVE_REQUEST)
                    .isRead(false)
                    .createdAt(LocalDateTime.now())
                    .build();
            notificationRepository.save(notif);
        }


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
        //User userRecipient = employee.getUser();
        employee.setUser(null);
        User user = employee.getUser();
        if (user != null) {
            // Remove the link from both sides
            employee.setUser(null);
            user.setEmployee(null);

            // Optional: save user to persist the change
            userRepository.save(user);
        }

        User authenticatedUser = getAuthenticatedUser();

        if (authenticatedUser != null ) {
            Notification notif = Notification.builder()
                    .recipient(authenticatedUser)
                    .title("Employee Unlinked Successfully")
                    .message("You successfully unlinked employee information from the user : " + employee.getFirstName() + " " + employee.getLastName())
                    .type(NotificationType.LEAVE_REQUEST)
                    .isRead(false)
                    .createdAt(LocalDateTime.now())
                    .build();
            notificationRepository.save(notif);
        }

        employeeRepository.save(employee);

    }
    @Auditable(action = "EMPLOYEE_DELETED", details = "delete an employee successfully")
    @Override
    public void deleteEmployee(Long id) {
        // Find the Employee and Contract by ID
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        Contract contract = contractRepository.findByEmployeeId(id)
                .orElseThrow(() -> new RuntimeException("Contract not found"));
        if (employee.getUser() != null) {
            Notification notif = Notification.builder()
                    .recipient(employee.getUser())
                    .title("Employee Deleted")
                    .message("Your employee profile has been removed.")
                    .type(NotificationType.EMPLOYEE_EVENT)
                    .build();
            notificationRepository.save(notif);
            throw new IllegalStateException("Cannot delete employee linked to a user. Unlink first.");
        }

        User authenticatedUser = getAuthenticatedUser();

        if (authenticatedUser != null ) {
            Notification notif = Notification.builder()
                    .recipient(authenticatedUser)
                    .title("Employee deleted")
                    .message("You successfully deleted employee info for: " + employee.getFirstName() + " " + employee.getLastName())
                    .type(NotificationType.LEAVE_REQUEST)
                    .isRead(false)
                    .createdAt(LocalDateTime.now())
                    .build();
            notificationRepository.save(notif);
        }

        // Delete the associated Contract and Employee from the database
        contractRepository.delete(contract);
        employeeRepository.delete(employee);
    }

    public void uploadEmployeeImage(Long employeeId, MultipartFile imageFile) throws IOException {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        String uploadsDir = "uploads/images/";
        Files.createDirectories(Paths.get(uploadsDir));

        String fileExtension = StringUtils.getFilenameExtension(imageFile.getOriginalFilename());
        String fileName = "employee_" + employeeId + "." + fileExtension;
        Path filePath = Paths.get(uploadsDir, fileName);

        // Écrase l'ancienne image si elle existe
        Files.write(filePath, imageFile.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);

        // Enregistre le chemin dans l'entité
        employee.setProfileImageUrl("/uploads/images/" + fileName);
        employeeRepository.save(employee);
    }

    public void uploadProfileImageForAuthenticatedUser(MultipartFile imageFile) throws IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Employee employee = employeeRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Employee not found for user"));

        uploadEmployeeImage(employee.getId(), imageFile);
    }

}
