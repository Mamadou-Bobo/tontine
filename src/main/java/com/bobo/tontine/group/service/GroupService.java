package com.bobo.tontine.group.service;

import com.bobo.tontine.group.entity.Group;
import org.springframework.http.ResponseEntity;

import java.security.Principal;

/**
 * @author Mamadou Bobo on 28/10/2022
 * @Project Tontine
 */
public interface GroupService {
    ResponseEntity<Object> addGroup(Group group, Principal principal);
    ResponseEntity<Object> addUserToGroup(String username, String groupName, String groupCreatorUsername);
}
