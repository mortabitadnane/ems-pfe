package com.maroc_ouvrage.semployee.service;
import com.maroc_ouvrage.semployee.dto.CongeDTO;
import com.maroc_ouvrage.semployee.model.CongeStatus;
import com.maroc_ouvrage.semployee.model.User;

import java.util.List;


public interface CongeService {

    // Create a new leave request (Conge)
    CongeDTO createConge(CongeDTO congeDTO);

    // Update an existing leave request by contract ID
    CongeDTO updateConge(Long congeId, CongeDTO congeDTO);

    // Get a leave request by contract ID
    CongeDTO getCongeById(Long congeId);

    // Get a leave request by employee CIN
    List<CongeDTO> getCongeByEmployeeCin(String employeeCin);

    void updateStatus(Long congeId, CongeStatus status);

    // Get all leave requests
    List<CongeDTO> getAllConge();

    // Delete a leave request by contract ID
    void deleteConge(Long congeId);

    CongeDTO updateMyConge(Long congeId, CongeDTO dto, User user);
    void deleteMyConge(Long congeId, User user);
    List<CongeDTO> getMyLeaves(User user);
    CongeDTO createMyConge(CongeDTO congeDTO, User user);

}


