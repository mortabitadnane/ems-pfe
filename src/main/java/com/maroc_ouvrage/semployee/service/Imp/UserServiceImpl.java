package com.maroc_ouvrage.semployee.service.Imp;

import com.maroc_ouvrage.semployee.audit.Auditable;
import com.maroc_ouvrage.semployee.dto.UserDTO;
import com.maroc_ouvrage.semployee.mapper.UserMapper;
import com.maroc_ouvrage.semployee.model.Role;
import com.maroc_ouvrage.semployee.model.User;
import com.maroc_ouvrage.semployee.repo.RoleRepository;
import com.maroc_ouvrage.semployee.repo.UserRepository;
import com.maroc_ouvrage.semployee.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

        private final UserRepository userRepository;
        private final UserMapper userMapper;
        private final BCryptPasswordEncoder passwordEncoder;
        private final RoleRepository roleRepository;
        private final EmailService emailService;



        private Set<Role> mapRoleNamesToEntities(Set<String> roleNames) {
            return roleNames.stream()
                    .map(name -> roleRepository.findByName(name)
                            .orElseThrow(() -> new RuntimeException("Role not found: " + name)))
                    .collect(Collectors.toSet());
        }
        @Auditable(action = "USER_CREATED", details = "create a user successfully")
        @Override
        @Transactional
        public UserDTO createUser(UserDTO dto) {
            if (userRepository.existsByUsername(dto.getUsername())) {
                throw new IllegalArgumentException("Username already exists");
            }
            User user = userMapper.toEntity(dto);
            String rawPassword = dto.getPassword();
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
            user.setRoles(mapRoleNamesToEntities(dto.getRoles())); // <== Here!
            User savedUser = userRepository.save(user);
            emailService.sendAccountEmail(savedUser.getEmail(), savedUser.getUsername(), rawPassword);
            return userMapper.toDTO(savedUser);
        }


        @Override
        public List<UserDTO> getAllUsers() {
            return userRepository.findAll().stream()
                    .map(userMapper::toDTO)
                    .collect(Collectors.toList());
        }

        @Override
        public Optional<UserDTO> getUserById(Long id) {
            return userRepository.findById(id).map(userMapper::toDTO);
        }

        @Auditable(action = "USER_UPDATED", details = "update a user successfully")
        @Transactional
        @Override
        public UserDTO updateUser(Long id, UserDTO dto) {
            User existingUser = userRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            // Update fields from DTO
            userMapper.updateEntityFromDTO(dto, existingUser);

            if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
                existingUser.setPassword(passwordEncoder.encode(dto.getPassword()));
            }

            if (dto.getRoles() != null) {
                existingUser.setRoles(mapRoleNamesToEntities(dto.getRoles()));
            }

            User updatedUser = userRepository.save(existingUser);
            return userMapper.toDTO(updatedUser);
        }

        @Auditable(action = "USER_DELETED", details = "delete a user successfully")
        @Override
        public void deleteUser(Long id) {
                userRepository.deleteById(id);
            }

        @Override
        public Optional<User> getUserByUsername(String username) {
            return userRepository.findByUsername(username);
        }
}


