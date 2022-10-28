package com.bobo.tontine.profile.service;

import com.bobo.tontine.profile.entity.Role;
import com.bobo.tontine.profile.entity.User;
import com.bobo.tontine.profile.repository.RoleRepository;
import com.bobo.tontine.profile.repository.UserRepository;
import com.bobo.tontine.shared.exception.ResourceFoundException;
import com.bobo.tontine.shared.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Mamadou Bobo on 21/10/2022
 * @Project Tontine
 */

@Service
@RequiredArgsConstructor
@Slf4j
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    @Override
    public Role addRole(Role role) {
        log.info("adding role {}", role.getName());

        if(roleRepository.findByName(role.getName()).isPresent()) {
            throw new ResourceFoundException(role.getName() + " already exist");
        }

        return roleRepository.save(role);
    }

    @Override
    public List<Role> getAllRoles() {
        log.info("retrieving all roles");
        return roleRepository.findAll();
    }

    @Override
    public void giveRoleToUser(String username, String roleName) {
        log.info("giving role {} to user {}", roleName,username);

        Role role = roleRepository.findByName(roleName).orElseThrow(() ->
                new ResourceNotFoundException(roleName + " doesn't exist"));

        User user = userRepository.findByUsername(username).orElseThrow(() ->
                new ResourceNotFoundException(username + " doesn't exist"));

        user.getRoles().add(role);
    }
}
