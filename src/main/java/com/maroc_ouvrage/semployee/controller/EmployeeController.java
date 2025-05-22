package com.maroc_ouvrage.semployee.controller;

import com.maroc_ouvrage.semployee.dto.EmployeecontractDTO;
import com.maroc_ouvrage.semployee.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
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


