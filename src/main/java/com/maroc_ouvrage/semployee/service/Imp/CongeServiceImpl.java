package com.maroc_ouvrage.semployee.service.Imp;

import com.maroc_ouvrage.semployee.audit.Auditable;
import com.maroc_ouvrage.semployee.dto.CongeDTO;
import com.maroc_ouvrage.semployee.mapper.CongeMapper;
import com.maroc_ouvrage.semployee.model.*;
import com.maroc_ouvrage.semployee.repo.CongeRepository;
import com.maroc_ouvrage.semployee.repo.EmployeeRepository;
import com.maroc_ouvrage.semployee.repo.NotificationRepository;
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
    private final NotificationRepository notificationRepository;


    @Autowired
    public CongeServiceImpl(CongeRepository congeRepository, EmployeeRepository employeeRepository, CongeMapper congeMapper, NotificationRepository notificationRepository) {
        this.congeRepository = congeRepository;
        this.congeMapper = congeMapper;
        this.employeeRepository = employeeRepository;
        this.notificationRepository = notificationRepository;
    }
    @Auditable(action = "LEAVE_CREATED", details = "create a leave successfully")
    @Override
    public CongeDTO createConge(CongeDTO congeDTO) {
        // 🔍 1. Use employeeCin from DTO to fetch employee
        Employee employee = employeeRepository.findByCin(congeDTO.getEmployeeCin())
                .orElseThrow(() -> new EntityNotFoundException("Employee not found with CIN: " + congeDTO.getEmployeeCin()));

        // 🧱 2. Convert DTO to entity (employee is not set yet)
        Conge conge = congeMapper.toEntity(congeDTO);

        User userRecipient = employee.getUser();  // <-- make sure this is the right getter

        if (userRecipient != null) {
            // 5. Create and save notification with User as recipient
            Notification notif = Notification.builder()
                    .recipient(userRecipient)
                    .title("Leave Request Created")
                    .message("Your leave request has been created.")
                    .type(NotificationType.LEAVE_REQUEST)
                    .build();

            notificationRepository.save(notif);
        }

        // 💾 4. Save and return
        return congeMapper.toDto(congeRepository.save(conge));

    }

    @Auditable(action = "LEAVE_UPDATED", details = "update a leave successfully")
    @Override
    public CongeDTO updateConge(Long congeId, CongeDTO congeDTO) {
        // Find the existing Conge entity by ID
        Conge conge = congeRepository.findById(congeId)
                .orElseThrow(() -> new RuntimeException("Conge not found"));

        // Update the Conge entity using the CongeDTO
        congeMapper.updateEntityFromDto(congeDTO, conge);

        // Save the updated Conge entity
        conge = congeRepository.save(conge);

        User userRecipient = conge.getEmployee().getUser();

        if (userRecipient != null) {
            // 5. Create and save notification with User as recipient
            Notification notif = Notification.builder()
                    .recipient(userRecipient)
                    .title("Leave Request updated")
                    .message("Your leave request has been updated.")
                    .type(NotificationType.LEAVE_REQUEST)
                    .build();

            notificationRepository.save(notif);
        }


        // Convert the updated Conge entity back to CongeDTO and return
        return congeMapper.toDto(conge);
    }

    @Auditable(action = "LEAVE_UPDATED", details = "update a leave successfully")
    @Override
    public void updateStatus(Long congeId, CongeStatus status) {
        Conge conge = congeRepository.findById(congeId)
                .orElseThrow(() -> new RuntimeException("Conge not found"));
        conge.setStatus(status);

        congeRepository.save(conge);

        User userRecipient = conge.getEmployee().getUser();

        if (userRecipient != null) {
            String message = (status == CongeStatus.APPROVED) ? "Your leave request has been approved." : "Your leave request has been denied.";
            Notification notif = Notification.builder()
                    .recipient(userRecipient)
                    .title("Leave Request " + status.name())
                    .message(message)
                    .type(NotificationType.LEAVE_REQUEST)
                    .build();
            notificationRepository.save(notif);
        }
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
    @Auditable(action = "LEAVE_DELETED", details = "delete a leave successfully")
    @Override
    public void deleteConge(Long congeId) {
        // Find the Conge entity by ID
        Conge conge = congeRepository.findById(congeId)
                .orElseThrow(() -> new RuntimeException("Conge not found"));

        // Delete the Conge entity
        congeRepository.delete(conge);

        User userRecipient = conge.getEmployee().getUser();

        if (userRecipient != null) {
            // 5. Create and save notification with User as recipient
            Notification notif = Notification.builder()
                    .recipient(userRecipient)
                    .title("Leave deleted")
                    .message("Your leave request has been deleted.")
                    .type(NotificationType.LEAVE_REQUEST)
                    .build();

            notificationRepository.save(notif);
        }
    }

    public List<CongeDTO> getMyLeaves(User user) {
        String cin = user.getEmployee().getCin();
        List<Conge> conges = congeRepository.findByEmployeeCinOrderByStartDateDesc(cin);

        return conges.stream()
                .map(congeMapper::toDto)
                .collect(Collectors.toList());
    }

    @Auditable(action = "LEAVE_CREATED", details = "create a leave successfully")
    public CongeDTO createMyConge(CongeDTO congeDTO, User user) {
        Employee employee = user.getEmployee();
        if (employee == null) {
            throw new RuntimeException("Employee not found for current user");
        }

        Conge conge = congeMapper.toEntity(congeDTO);
        conge.setEmployee(employee); // link leave to employee
        conge.setStatus(CongeStatus.PENDING);

        conge = congeRepository.save(conge);

        // Notification
        User userRecipient = employee.getUser();
        if (userRecipient != null) {
            Notification notif = Notification.builder()
                    .recipient(userRecipient)
                    .title("Demande de congé créée")
                    .message("Votre demande de congé a été créée.")
                    .type(NotificationType.LEAVE_REQUEST)
                    .build();
            notificationRepository.save(notif);
        }

        return congeMapper.toDto(conge);
    }

    @Auditable(action = "LEAVE_UPDATED", details = "update a leave successfully")
    public CongeDTO updateMyConge(Long congeId, CongeDTO congeDTO, User user) {
        Conge conge = congeRepository.findById(congeId)
                .orElseThrow(() -> new RuntimeException("Conge not found"));

        // Ownership check
        if (!conge.getEmployee().getCin().equals(user.getEmployee().getCin())) {
            throw new RuntimeException("Vous ne pouvez modifier que vos propres congés");
        }

        // Only pending leaves can be updated
        if (conge.getStatus() != CongeStatus.PENDING) {
            throw new RuntimeException("Seuls les congés en attente peuvent être modifiés");
        }

        congeMapper.updateEntityFromDto(congeDTO, conge);
        conge = congeRepository.save(conge);

        // Notification
        User userRecipient = conge.getEmployee().getUser();
        if (userRecipient != null) {
            Notification notif = Notification.builder()
                    .recipient(userRecipient)
                    .title("Demande de congé mise à jour")
                    .message("Votre demande de congé a été mise à jour.")
                    .type(NotificationType.LEAVE_REQUEST)
                    .build();
            notificationRepository.save(notif);
        }

        return congeMapper.toDto(conge);
    }

    @Auditable(action = "LEAVE_DELETED", details = "delete a leave successfully")
    public void deleteMyConge(Long congeId, User user) {
        Conge conge = congeRepository.findById(congeId)
                .orElseThrow(() -> new RuntimeException("Conge not found"));

        // Ownership check
        if (!conge.getEmployee().getCin().equals(user.getEmployee().getCin())) {
            throw new RuntimeException("Vous ne pouvez supprimer que vos propres congés");
        }

        // Only pending leaves can be deleted
        if (conge.getStatus() != CongeStatus.PENDING) {
            throw new RuntimeException("Seuls les congés en attente peuvent être supprimés");
        }

        congeRepository.delete(conge);

        // Notification
        User userRecipient = conge.getEmployee().getUser();
        if (userRecipient != null) {
            Notification notif = Notification.builder()
                    .recipient(userRecipient)
                    .title("Demande de congé supprimée")
                    .message("Votre demande de congé a été supprimée.")
                    .type(NotificationType.LEAVE_REQUEST)
                    .build();
            notificationRepository.save(notif);
        }
    }

}
