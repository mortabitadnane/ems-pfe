package com.maroc_ouvrage.semployee.service;

import com.maroc_ouvrage.semployee.dto.CongeDTO;

import java.util.List;


public interface CongeService {

    // Create a new leave request (Conge)
    CongeDTO createConge(CongeDTO congeDTO);

    // Update an existing leave request by contract ID
    CongeDTO updateConge(Long congeId, CongeDTO congeDTO);

    // Get a leave request by contract ID
    CongeDTO getCongeById(Long congeId);

    // Get a leave request by employee CIN
    CongeDTO getCongeByEmployeeCin(String employeeCin);

    // Get all leave requests
    List<CongeDTO> getAllConge();

    // Delete a leave request by contract ID
    void deleteConge(Long congeId);
}


