package com.maroc_ouvrage.semployee.service.Imp;
import com.maroc_ouvrage.semployee.dto.ContractDTO;
import com.maroc_ouvrage.semployee.mapper.ContractMapper;
import com.maroc_ouvrage.semployee.repo.ContractRepository;
import com.maroc_ouvrage.semployee.model.Contract;
import com.maroc_ouvrage.semployee.service.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContractServiceImpl implements ContractService {

    private final ContractRepository contractRepository;
    private final ContractMapper contractMapper;

    @Autowired
    public ContractServiceImpl(ContractRepository contractRepository, ContractMapper contractMapper) {
        this.contractRepository = contractRepository;
        this.contractMapper = contractMapper;
    }

    @Override
    public ContractDTO createContract(ContractDTO contractDTO) {
        // Convert ContractDTO to Contract entity
        Contract contract = contractMapper.toEntityFromDTO(contractDTO);

        // Save the contract entity to the database
        contract = contractRepository.save(contract);

        // Convert the saved Contract entity back to ContractDTO and return
        return contractMapper.toDTOFromEntity(contract);
    }

    @Override
    public ContractDTO updateContract(Long contractId, ContractDTO contractDTO) {
        // Find the existing contract by ID
        Contract contract = contractRepository.findById(contractId)
                .orElseThrow(() -> new RuntimeException("Contract not found"));

        // Update the contract entity using the data from the DTO
        contractMapper.updateEntityFromDTO(contractDTO, contract);

        // Save the updated contract entity
        contract = contractRepository.save(contract);

        // Convert the updated Contract entity to ContractDTO and return
        return contractMapper.toDTOFromEntity(contract);
    }

    @Override
    public ContractDTO getContractById(Long contractId) {
        // Fetch the contract by ID
        Contract contract = contractRepository.findById(contractId)
                .orElseThrow(() -> new RuntimeException("Contract not found"));

        // Convert the Contract entity to ContractDTO and return
        return contractMapper.toDTOFromEntity(contract);
    }

    @Override
    public ContractDTO getContractByEmployeeId(Long employeeId) {
        // Fetch the contract by employee ID
        Contract contract = contractRepository.findByEmployeeId(employeeId)
                .orElseThrow(() -> new RuntimeException("Contract not found for employee"));

        // Convert the Contract entity to ContractDTO and return
        return contractMapper.toDTOFromEntity(contract);
    }

    @Override
    public void deleteContract(Long contractId) {
        // Find the contract to delete
        Contract contract = contractRepository.findById(contractId)
                .orElseThrow(() -> new RuntimeException("Contract not found"));

        // Delete the contract entity
        contractRepository.delete(contract);
    }

    @Override
    public List<ContractDTO> getAllContracts() {
        // Fetch all contracts from the database
        List<Contract> contracts = contractRepository.findAll();

        // Map the list of Contract entities to a list of ContractDTOs
        return contracts.stream()
                .map(contractMapper::toDTOFromEntity)
                .collect(Collectors.toList());
    }
}

