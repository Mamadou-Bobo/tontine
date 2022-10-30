package com.bobo.tontine.group.service;

import com.bobo.tontine.group.entity.Group;
import com.bobo.tontine.group.repository.GroupRepository;
import com.bobo.tontine.profile.entity.User;
import com.bobo.tontine.profile.repository.UserRepository;
import com.bobo.tontine.shared.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Date;

/**
 * @author Mamadou Bobo on 28/10/2022
 * @Project Tontine
 */

@Service
@Slf4j
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;
    private final UserRepository userRepository;

    @Override
    public ResponseEntity<Object> addGroup(Group group, Principal principal) {
        log.info("adding group {}", group.getName());

        if(groupRepository.findByName(group.getName()).isPresent()) {
            return new ResponseEntity<>("Group " + group.getName() + " already exists", HttpStatus.BAD_REQUEST);
        }

        User user = userRepository.findByUsername(principal.getName()).orElseThrow(() ->
                new ResourceNotFoundException("User " + principal.getName() + " does not exist"));

        group.setCreateBy(user.getId());
        group.setCreatedAt(new Date());

        return ResponseEntity.ok().body(groupRepository.save(group));
    }

    @Override
    public ResponseEntity<Object> addUserToGroup(String username, String groupName) {
        log.info("adding user {} to a new group", username);

        User user = userRepository.findByUsername(username).orElseThrow(() ->
                new ResourceNotFoundException("User " + username + " does not exist"));

        Group group = groupRepository.findByName(groupName).orElseThrow(() ->
                new ResourceNotFoundException("Group " + groupName + " does not exist"));

        if(group.getMembers().contains(user)) {
            return ResponseEntity.badRequest().body("User " + username + " already exist in " +
                    groupName);
        }

        group.getMembers().add(user);

        return ResponseEntity.ok(groupRepository.save(group));
    }
}
