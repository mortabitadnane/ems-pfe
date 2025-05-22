package com.maroc_ouvrage.semployee.controller;

import com.maroc_ouvrage.semployee.dto.CongeDTO;
import com.maroc_ouvrage.semployee.service.CongeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    // Get a leave request by contract ID
    @GetMapping("/{congeId}")
    public ResponseEntity<CongeDTO> getCongeById(@PathVariable Long congeId) {
        CongeDTO congeDTO = congeService.getCongeById(congeId);
        return new ResponseEntity<>(congeDTO, HttpStatus.OK);
    }

    // Get a leave request by employee CIN
    @GetMapping("/employee/{employeeCin}")
    public ResponseEntity<CongeDTO> getCongeByEmployeeCin(@PathVariable String employeeCin) {
        CongeDTO congeDTO = congeService.getCongeByEmployeeCin(employeeCin);
        return new ResponseEntity<>(congeDTO, HttpStatus.OK);
    }

    // Get all leave requests
    @GetMapping
    public ResponseEntity<List<CongeDTO>> getAllConge() {
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

