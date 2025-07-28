package com.maroc_ouvrage.semployee.service;

import com.maroc_ouvrage.semployee.dto.UserDTO;
import com.maroc_ouvrage.semployee.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    UserDTO createUser(UserDTO dto); // inscription manuelle ou par admin

    List<UserDTO> getAllUsers();

    Optional<UserDTO> getUserById(Long id);

    UserDTO updateUser(Long id, UserDTO dto);

    void deleteUser(Long id);

    Optional<User> getUserByUsername(String username);
}
