package com.project.UDMS.dto;

import com.project.UDMS.resource.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserDto {
        private int id;
        @NotBlank
        private String userId;
        @NotBlank
        private String username;
        @NotBlank
        private String password;
        @NotNull
        private Role role;
}
