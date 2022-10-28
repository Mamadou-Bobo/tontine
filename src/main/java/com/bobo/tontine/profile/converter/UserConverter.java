package com.bobo.tontine.profile.converter;

import com.bobo.tontine.profile.dto.UserDto;
import com.bobo.tontine.profile.entity.User;

/**
 * @author Mamadou Bobo on 21/10/2022
 * @Project Tontine
 */
public class UserConverter {

    public static User convertUserDtoToEntity(UserDto userDto) {
        return User.builder()
                .username(userDto.getUsername())
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .roles(userDto.getRoles())
                .build();
    }

}
