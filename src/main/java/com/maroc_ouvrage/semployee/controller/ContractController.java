package com.maroc_ouvrage.semployee.controller;

import com.maroc_ouvrage.semployee.dto.ContractDTO;
import com.maroc_ouvrage.semployee.service.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
@RestController
@RequestMapping("/api/contracts")
public class ContractController {

    private final ContractService contractService;

    @Autowired
    public ContractController(ContractService contractService) {
        this.contractService = contractService;
    }

    // Create a new contract
    @PostMapping
    public ResponseEntity<ContractDTO> createContract(@RequestBody ContractDTO contractDTO) {
        ContractDTO createdContract = contractService.createContract(contractDTO);
        return new ResponseEntity<>(createdContract, HttpStatus.CREATED);
    }

    // Update an existing contract
    @PutMapping("/{contractId}")
    public ResponseEntity<ContractDTO> updateContract(@PathVariable Long contractId,
                                                      @RequestBody ContractDTO contractDTO) {
        ContractDTO updatedContract = contractService.updateContract(contractId, contractDTO);
        return new ResponseEntity<>(updatedContract, HttpStatus.OK);
    }

    // Get a contract by contract ID
    @GetMapping("/{contractId}")
    public ResponseEntity<ContractDTO> getContractById(@PathVariable Long contractId) {
        ContractDTO contractDTO = contractService.getContractById(contractId);
        return new ResponseEntity<>(contractDTO, HttpStatus.OK);
    }

    // Get a contract by employee ID
    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<ContractDTO> getContractByEmployeeId(@PathVariable Long employeeId) {
        ContractDTO contractDTO = contractService.getContractByEmployeeId(employeeId);
        return new ResponseEntity<>(contractDTO, HttpStatus.OK);
    }

    // Get all contracts
    @GetMapping
    public ResponseEntity<List<ContractDTO>> getAllContracts() {
        List<ContractDTO> contracts = contractService.getAllContracts();
        return new ResponseEntity<>(contracts, HttpStatus.OK);
    }

    // Delete a contract by contract ID
    @DeleteMapping("/{contractId}")
    public ResponseEntity<Void> deleteContract(@PathVariable Long contractId) {
        contractService.deleteContract(contractId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
