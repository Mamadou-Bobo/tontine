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
import java.util.stream.Collectors;

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

        if(group.getName().length() < 3) {
            return ResponseEntity.badRequest().body("Please insert at least 3 characters");
        }

        if(userRepository.findByUsername(principal.getName()).isEmpty()) {
            return ResponseEntity.badRequest().body("User " + principal.getName() + " not found");
        }

        User user = userRepository.findByUsername(principal.getName()).get();

        if(groupRepository.findGroupCreatorByUsername(principal.getName()) != null) {
            return new ResponseEntity<>("You already have a group with the name " + group.getName(), HttpStatus.BAD_REQUEST);
        }

        group.setCreatedBy(user.getId());
        group.setCreatedAt(new Date());

        return ResponseEntity.ok().body(groupRepository.save(group));
    }

    @Override
    public ResponseEntity<Object> addUserToGroup(String username, String groupName, String groupCreatorUsername) {
        log.info("adding user {} to a new group", username);

        Group group = groupRepository.findGroupCreatorByUsername(groupCreatorUsername);

        User user = userRepository.findByUsername(username).orElseThrow(() ->
                new ResourceNotFoundException("User " + username + " does not exist"));

        //Group group = groupRepository.findByName(groupName).orElseThrow(() ->
                //new ResourceNotFoundException("Group " + groupName + " does not exist"));

        if(group.getMembers().contains(user)) {
            return ResponseEntity.badRequest().body("User " + username + " already exist in " +
                    groupName);
        }

        group.getMembers().add(user);
        groupRepository.save(group);

        return ResponseEntity.ok("User " + username + " added with success");
    }
}
