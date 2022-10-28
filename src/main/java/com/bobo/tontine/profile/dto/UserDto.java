package com.bobo.tontine.profile.dto;

import com.bobo.tontine.profile.entity.Role;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author Mamadou Bobo on 21/10/2022
 * @Project Tontine
 */

@Builder
@Data
public class UserDto {
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private List<Role> roles;
}
