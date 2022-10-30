package com.bobo.tontine.profile.controller;

import com.bobo.tontine.profile.converter.UserConverter;
import com.bobo.tontine.profile.dto.UserDto;
import com.bobo.tontine.profile.entity.User;
import com.bobo.tontine.profile.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.security.Principal;
import java.util.List;

import static com.bobo.tontine.shared.utils.Constant.BASE_URL;

/**
 * @author Mamadou Bobo on 21/10/2022
 * @Project Tontine
 */

@RequestMapping(value = BASE_URL+"/user")
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/add")
    public ResponseEntity<User> addUser(@RequestBody UserDto userDto) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path(BASE_URL+"/user/add").toUriString());
        return ResponseEntity.created(uri).body(userService.addUser(UserConverter.convertUserDtoToEntity(userDto)));
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<User>> getAllUsers() {
        //URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path(BASE_URL+"/getAll").toUriString());
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PostMapping("/update")
    public ResponseEntity<Object> updateUser(@RequestBody UserDto userDto, Principal principal) {
        //URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path(BASE_URL+"/update").toUriString());
        return userService.updateUser(UserConverter.convertUserDtoToEntity(userDto), principal);
    }

}
