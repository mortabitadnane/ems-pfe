package com.maroc_ouvrage.semployee.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private Long id;           // The user's unique identifier
    private String email;      // The user's email
    private String fullName;   // The user's full name
    private String role;       // The user's role (e.g., admin, user)
}

