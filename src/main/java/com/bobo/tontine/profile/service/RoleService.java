package com.bobo.tontine.profile.service;

import com.bobo.tontine.profile.entity.Role;

import java.util.List;

/**
 * @author Mamadou Bobo on 21/10/2022
 * @Project Tontine
 */
public interface RoleService {
    Role addRole(Role role);
    List<Role> getAllRoles();
    void giveRoleToUser(String username, String roleName);
}
