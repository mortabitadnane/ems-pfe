package com.maroc_ouvrage.semployee.service.Imp;

import com.maroc_ouvrage.semployee.dto.CongeDTO;
import com.maroc_ouvrage.semployee.mapper.CongeMapper;
import com.maroc_ouvrage.semployee.model.Conge;
import com.maroc_ouvrage.semployee.model.CongeStatus;
import com.maroc_ouvrage.semployee.model.Employee;
import com.maroc_ouvrage.semployee.repo.CongeRepository;
import com.maroc_ouvrage.semployee.repo.EmployeeRepository;
import com.maroc_ouvrage.semployee.service.CongeService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CongeServiceImpl implements CongeService {

    private final CongeRepository congeRepository;
    private final CongeMapper congeMapper;
    private final EmployeeRepository employeeRepository;


    @Autowired
    public CongeServiceImpl(CongeRepository congeRepository, EmployeeRepository employeeRepository, CongeMapper congeMapper) {
        this.congeRepository = congeRepository;
        this.congeMapper = congeMapper;
        this.employeeRepository = employeeRepository;
    }

    @Override
    public CongeDTO createConge(CongeDTO congeDTO) {
        // 🔍 1. Use employeeCin from DTO to fetch employee
        Employee employee = employeeRepository.findByCin(congeDTO.getEmployeeCin())
                .orElseThrow(() -> new EntityNotFoundException("Employee not found with CIN: " + congeDTO.getEmployeeCin()));

        // 🧱 2. Convert DTO to entity (employee is not set yet)
        Conge conge = congeMapper.toEntity(congeDTO);

        // 🔗 3. Set the employee manually
        conge.setEmployee(employee);

        // 💾 4. Save and return
        return congeMapper.toDto(congeRepository.save(conge));
    }


    @Override
    public CongeDTO updateConge(Long congeId, CongeDTO congeDTO) {
        // Find the existing Conge entity by ID
        Conge conge = congeRepository.findById(congeId)
                .orElseThrow(() -> new RuntimeException("Conge not found"));

        // Update the Conge entity using the CongeDTO
        congeMapper.updateEntityFromDto(congeDTO, conge);

        // Save the updated Conge entity
        conge = congeRepository.save(conge);

        // Convert the updated Conge entity back to CongeDTO and return
        return congeMapper.toDto(conge);
    }

    @Override
    public void updateStatus(Long congeId, CongeStatus status) {
        Conge conge = congeRepository.findById(congeId)
                .orElseThrow(() -> new RuntimeException("Conge not found"));
        conge.setStatus(status);
        congeRepository.save(conge);
    }

    @Override
    public CongeDTO getCongeById(Long congeId) {
        // Find the Conge entity by ID
        Conge conge = congeRepository.findById(congeId)
                .orElseThrow(() -> new RuntimeException("Conge not found"));

        // Convert Conge entity to CongeDTO and return
        return congeMapper.toDto(conge);
    }


    public List<CongeDTO> getCongeByEmployeeCin(String employeeCin) {
        List<Conge> conges = congeRepository.findByEmployeeCinOrderByStartDateDesc(employeeCin);
        if (conges.isEmpty()) {
            throw new RuntimeException("No conges found for employee with CIN: " + employeeCin);
        }
        return conges.stream()
                .map(congeMapper::toDto)
                .collect(Collectors.toList());
    }


    @Override
    public List<CongeDTO> getAllConge() {
        // Fetch all Conge entities
        List<Conge> conges = congeRepository.findAll();

        // Convert the list of Conge entities to a list of CongeDTOs
        return conges.stream()
                .map(congeMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteConge(Long congeId) {
        // Find the Conge entity by ID
        Conge conge = congeRepository.findById(congeId)
                .orElseThrow(() -> new RuntimeException("Conge not found"));

        // Delete the Conge entity
        congeRepository.delete(conge);
    }
}
