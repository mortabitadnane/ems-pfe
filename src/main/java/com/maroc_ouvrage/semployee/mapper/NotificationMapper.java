package com.maroc_ouvrage.semployee.mapper;


import com.maroc_ouvrage.semployee.dto.NotificationDTO;
import com.maroc_ouvrage.semployee.model.Notification;
import com.maroc_ouvrage.semployee.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface NotificationMapper {

    @Mapping(source = "recipient.id", target = "recipientId")
    NotificationDTO toDTO(Notification notification);

    @Mapping(target = "recipient", expression = "java(userFromId(notificationDTO.getRecipientId()))")
    Notification toEntity(NotificationDTO notificationDTO);

    default User userFromId(Long id) {
        if (id == null) {
            return null;
        }
        User user = new User();
        user.setId(id);
        return user;
    }
}




