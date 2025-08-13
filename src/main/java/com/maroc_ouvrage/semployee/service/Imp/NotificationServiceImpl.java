package com.maroc_ouvrage.semployee.service.Imp;


import com.maroc_ouvrage.semployee.dto.NotificationDTO;
import com.maroc_ouvrage.semployee.exception.NotificationNotFoundException;
import com.maroc_ouvrage.semployee.exception.UserNotFoundException;
import com.maroc_ouvrage.semployee.mapper.NotificationMapper;
import com.maroc_ouvrage.semployee.model.Notification;
import com.maroc_ouvrage.semployee.model.User;
import com.maroc_ouvrage.semployee.repo.NotificationRepository;
import com.maroc_ouvrage.semployee.repo.UserRepository;
import com.maroc_ouvrage.semployee.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;
    private final NotificationMapper notificationMapper;

    @Override
    public NotificationDTO createNotification(NotificationDTO dto) {
        User recipient = userRepository.findById(dto.getRecipientId())
                .orElseThrow(() -> new UserNotFoundException(dto.getRecipientId()));

        // Use the injected instance, not the class name
        Notification notification = notificationMapper.toEntity(dto);
        notification.setRecipient(recipient);
        notification.setCreatedAt(LocalDateTime.now());
        notification.setRead(false);

        Notification saved = notificationRepository.save(notification);
        return notificationMapper.toDTO(saved);
    }

    @Override
    public List<NotificationDTO> getUserNotifications(Long userId) {
        return notificationRepository.findByRecipientIdOrderByCreatedAtDesc(userId)
                .stream()
                .map(notificationMapper::toDTO)  // use injected instance here too
                .collect(Collectors.toList());
    }

    @Override
    public void markAsRead(Long id) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() ->  new NotificationNotFoundException(id));
        notification.setRead(true);
        notificationRepository.save(notification);
    }
}

