package com.maroc_ouvrage.semployee.controller;

import com.maroc_ouvrage.semployee.dto.EmployeecontractDTO;
import com.maroc_ouvrage.semployee.model.Employee;
import com.maroc_ouvrage.semployee.model.User;
import com.maroc_ouvrage.semployee.repo.EmployeeRepository;
import com.maroc_ouvrage.semployee.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeController(EmployeeService employeeService, EmployeeRepository employeeRepository) {
        this.employeeService = employeeService;
        this.employeeRepository = employeeRepository;
    }

    @PostMapping
    public ResponseEntity<EmployeecontractDTO> createEmployee(@RequestBody EmployeecontractDTO employeecontractDTO) {
        EmployeecontractDTO createdEmployee = employeeService.createEmployeeWithContract(employeecontractDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdEmployee);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeecontractDTO> updateEmployee(@PathVariable Long id, @RequestBody EmployeecontractDTO employeecontractDTO) {
        EmployeecontractDTO updatedEmployee = employeeService.updateEmployee(id, employeecontractDTO);
        return ResponseEntity.ok(updatedEmployee);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeecontractDTO> getEmployeeById(@PathVariable Long id) {
        EmployeecontractDTO employeecontractDTO = employeeService.getEmployeeById(id);
        return ResponseEntity.ok(employeecontractDTO);
    }

    @PostMapping("/{id}/upload-image")
    public ResponseEntity<String> uploadImage(@PathVariable Long id, @RequestParam("image") MultipartFile imageFile) {
        try {
            employeeService.uploadEmployeeImage(id, imageFile);
            return ResponseEntity.ok("Image uploaded successfully");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload image");
        }
    }

    @PostMapping("/upload-image")
    public ResponseEntity<String> uploadProfileImage(@RequestParam("image") MultipartFile imageFile) {
        try {
            employeeService.uploadProfileImageForAuthenticatedUser(imageFile);
            return ResponseEntity.ok("Image uploaded successfully");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload image");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }





    @GetMapping("/me")
    public ResponseEntity<EmployeecontractDTO> getMyEmployee(Authentication authentication) {
        String username = authentication.getName();
        EmployeecontractDTO employeeDTO = employeeService.getEmployeeByUsername(username);
        return ResponseEntity.ok(employeeDTO);
    }
    @PutMapping("/{id}/unlink-user")
    public ResponseEntity<Void> unlinkUser(@PathVariable Long id) {
        employeeService.unlinkUserFromEmployee(id);
        return ResponseEntity.noContent().build();
    }



    @GetMapping
    public ResponseEntity<List<EmployeecontractDTO>> getAllEmployees() {
        List<EmployeecontractDTO> employees = employeeService.getAllEmployees();
        return ResponseEntity.ok(employees);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }
}


