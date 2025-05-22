package com.maroc_ouvrage.semployee.service;

import com.maroc_ouvrage.semployee.dto.ContractDTO;

import java.util.List;

public interface ContractService {

    // Create a new contract
    ContractDTO createContract(ContractDTO contractDTO);

    // Update an existing contract by contract ID
    ContractDTO updateContract(Long contractId, ContractDTO contractDTO);

    // Get a contract by contract ID
    ContractDTO getContractById(Long contractId);

    // Get a contract by employee ID
    ContractDTO getContractByEmployeeId(Long employeeId);

    // Delete a contract by contract ID
    void deleteContract(Long contractId);

    // Optional: List all contracts
    List<ContractDTO> getAllContracts();
}

