package com.maroc_ouvrage.semployee.mapper;

import com.maroc_ouvrage.semployee.dto.UserDTO;
import com.maroc_ouvrage.semployee.model.Role;
import com.maroc_ouvrage.semployee.model.User;
import org.mapstruct.*;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(source = "id", target = "id")
    @Mapping(target = "roles", source = "roles", qualifiedByName = "rolesToStrings")
    UserDTO toDTO(User user);

    @Mapping(source = "id", target = "id")
    @Mapping(target = "roles", source = "roles", qualifiedByName = "stringsToRoles")
    User toEntity(UserDTO dto);

    @Mapping(source = "id", target = "id")
    @Mapping(target = "roles", ignore = true)
    void updateEntityFromDTO(UserDTO dto, @MappingTarget User user);

    // Convertit Set<Role> → Set<String>
    @Named("rolesToStrings")
    default Set<String> rolesToStrings(Set<Role> roles) {
        if (roles == null) return Collections.emptySet();
        return roles.stream()
                .map(Role::getName)
                .collect(Collectors.toSet());
    }

    // Convertit Set<String> → Set<Role> (sans ID)
    @Named("stringsToRoles")
    default Set<Role> stringsToRoles(Set<String> roleNames) {
        if (roleNames == null) return Collections.emptySet();
        return roleNames.stream()
                .map(name -> {
                    Role r = new Role();
                    r.setName(name);
                    return r;
                })
                .collect(Collectors.toSet());
    }
}
