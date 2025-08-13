package com.maroc_ouvrage.semployee.controller;

import com.maroc_ouvrage.semployee.dto.CongeDTO;
import com.maroc_ouvrage.semployee.exception.UserNotFoundException;
import com.maroc_ouvrage.semployee.mapper.CongeMapper;
import com.maroc_ouvrage.semployee.model.Conge;
import com.maroc_ouvrage.semployee.model.User;
import com.maroc_ouvrage.semployee.repo.CongeRepository;
import com.maroc_ouvrage.semployee.repo.EmployeeRepository;
import com.maroc_ouvrage.semployee.repo.UserRepository;
import com.maroc_ouvrage.semployee.security.MyUserDetails;
import com.maroc_ouvrage.semployee.service.Imp.CongeServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
@RestController
@RequestMapping("/api/conges/my")
@RequiredArgsConstructor
public class EmployeeCongeController {

    private final CongeServiceImpl congeService;
    private final UserRepository userRepository;
    private final CongeRepository congeRepository;
    private final CongeMapper congeMapper;
    // Get all leaves of the logged-in employee
    @GetMapping
    public List<CongeDTO> getMyLeaves(Authentication auth) {
        MyUserDetails userDetails = (MyUserDetails) auth.getPrincipal();
        Long userId = userDetails.getId();  // your User's id

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        String cin = user.getEmployee().getCin();  // now safe
        List<Conge> conges = congeRepository.findByEmployeeCinOrderByStartDateDesc(cin);

        return conges.stream().map(congeMapper::toDto).collect(Collectors.toList());
    }


    // Create a new leave for the logged-in employee
    @PostMapping
    public CongeDTO createMyLeave(@Valid @RequestBody CongeDTO congeDTO,
                                  Authentication auth) {
        MyUserDetails userDetails = (MyUserDetails) auth.getPrincipal();
        Long userId = userDetails.getId();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        return congeService.createMyConge(congeDTO, user);
    }

    // Update a pending leave of the logged-in employee
    @PutMapping("/{id}")
    public CongeDTO updateMyLeave(@PathVariable Long id,
                                  @Valid @RequestBody CongeDTO congeDTO,
                                  Authentication auth) {
        MyUserDetails userDetails = (MyUserDetails) auth.getPrincipal();
        Long userId = userDetails.getId();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        return congeService.updateMyConge(id, congeDTO, user);
    }

    // Delete a pending leave of the logged-in employee
    @DeleteMapping("/{id}")
    public void deleteMyLeave(@PathVariable Long id,
                              Authentication auth) {
        MyUserDetails userDetails = (MyUserDetails) auth.getPrincipal();
        Long userId = userDetails.getId();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        congeService.deleteMyConge(id, user);
    }

}

