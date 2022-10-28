package com.bobo.tontine.profile.service;

import com.bobo.tontine.profile.entity.User;
import org.springframework.http.ResponseEntity;

import java.security.Principal;
import java.util.List;

/**
 * @author Mamadou Bobo on 21/10/2022
 * @Project Tontine
 */
public interface UserService {
    User addUser(User user);
    List<User> getAllUsers();
    ResponseEntity<Object> updateUser(User user, Principal principal);
}
