package com.maroc_ouvrage.semployee.service;

import com.maroc_ouvrage.semployee.dto.NotificationDTO;

import java.util.List;

public interface NotificationService {
    NotificationDTO createNotification(NotificationDTO dto);
    List<NotificationDTO> getUserNotifications(Long userId);
    void markAsRead(Long id);
}
