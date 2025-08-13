package com.maroc_ouvrage.semployee.controller;

import com.maroc_ouvrage.semployee.dto.NotificationDTO;
import com.maroc_ouvrage.semployee.security.MyUserDetails;
import com.maroc_ouvrage.semployee.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping
    public List<NotificationDTO> getMyNotifications(Authentication auth) {
        MyUserDetails userDetails = (MyUserDetails) auth.getPrincipal();
        Long userId = userDetails.getId();  // your User's id
        return notificationService.getUserNotifications(userId);
    }


    @PatchMapping("/{id}/read")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void markAsRead(@PathVariable Long id) {
        notificationService.markAsRead(id);
    }
}

