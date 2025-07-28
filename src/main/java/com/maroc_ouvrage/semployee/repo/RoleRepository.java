package com.maroc_ouvrage.semployee.repo;

import com.maroc_ouvrage.semployee.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(String name);
}
