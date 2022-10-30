package com.bobo.tontine.group.controller;

import com.bobo.tontine.group.converter.GroupConverter;
import com.bobo.tontine.group.dto.GroupDto;
import com.bobo.tontine.group.entity.Group;
import com.bobo.tontine.group.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.security.Principal;

import static com.bobo.tontine.shared.utils.Constant.BASE_URL;

/**
 * @author Mamadou Bobo on 28/10/2022
 * @Project Tontine
 */

@RestController
@RequestMapping(value = BASE_URL + "/group")
@RequiredArgsConstructor
public class GroupController {

    private final GroupService groupService;

    @PostMapping("/add")
    public ResponseEntity<Object> addGroup(@RequestBody GroupDto groupDto, Principal principal) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path(BASE_URL+"/group/add").toUriString());
        return ResponseEntity.created(uri).body(groupService.addGroup(GroupConverter.convertGroupDtoToEntity(groupDto),principal));
    }

    @PutMapping("/add-member")
    public ResponseEntity<Object> addUserToGroup(@RequestBody GroupDto groupDto) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path(BASE_URL+"/group/add-member").toUriString());
        return ResponseEntity.created(uri).body(groupService.addUserToGroup(groupDto.getUsername(),groupDto.getName()));
    }

}
