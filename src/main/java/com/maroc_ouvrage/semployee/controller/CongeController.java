package com.maroc_ouvrage.semployee.controller;
import com.maroc_ouvrage.semployee.dto.CongeDTO;
import com.maroc_ouvrage.semployee.model.CongeStatus;
import com.maroc_ouvrage.semployee.service.CongeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "http://localhost:4200",allowCredentials = "true")
@RestController
@RequestMapping("/api/conges")
public class CongeController {

    private final CongeService congeService;

    @Autowired
    public CongeController(CongeService congeService) {
        this.congeService = congeService;
    }

    // Create a new leave request (Conge)
    @PostMapping
    public ResponseEntity<CongeDTO> createConge(@RequestBody CongeDTO congeDTO) {
        CongeDTO createdConge = congeService.createConge(congeDTO);
        return new ResponseEntity<>(createdConge, HttpStatus.CREATED);
    }

    // Update an existing leave request by contract ID
    @PutMapping("/{congeId}")
    public ResponseEntity<CongeDTO> updateConge(@PathVariable Long congeId, @RequestBody CongeDTO congeDTO) {
        CongeDTO updatedConge = congeService.updateConge(congeId, congeDTO);
        return new ResponseEntity<>(updatedConge, HttpStatus.OK);
    }

    // Update only the status of a leave request (approve/deny)
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @PutMapping("/{congeId}/status")
    public ResponseEntity<Void> updateStatus(@PathVariable Long congeId, @RequestParam CongeStatus status) {
        congeService.updateStatus(congeId, status);
        return ResponseEntity.ok().build();
    }

    // Get a leave request by contract ID
    @PreAuthorize("hasAnyRole('USER')")
    @GetMapping("/{congeId}")
    public ResponseEntity<CongeDTO> getCongeById(@PathVariable Long congeId) {
        CongeDTO congeDTO = congeService.getCongeById(congeId);
        return new ResponseEntity<>(congeDTO, HttpStatus.OK);
    }

    // Get a leave request by employee CIN
    @GetMapping("/employee/{employeeCin}")
    public ResponseEntity<List<CongeDTO>> getCongeByEmployeeCin(@PathVariable String employeeCin) {
        List<CongeDTO> congeDTOs = congeService.getCongeByEmployeeCin(employeeCin);
        return new ResponseEntity<>(congeDTOs, HttpStatus.OK);
    }


    // Get all leave requests
    @GetMapping
    public ResponseEntity<List<CongeDTO>> getAllConge() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("Current Auth: " + auth);
        List<CongeDTO> congeDTOs = congeService.getAllConge();
        return new ResponseEntity<>(congeDTOs, HttpStatus.OK);
    }

    // Delete a leave request by contract ID
    @DeleteMapping("/{congeId}")
    public ResponseEntity<Void> deleteConge(@PathVariable Long congeId) {
        congeService.deleteConge(congeId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

