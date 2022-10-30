package com.bobo.tontine.profile.controller;

import com.bobo.tontine.profile.dto.RoleDto;
import com.bobo.tontine.profile.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static com.bobo.tontine.shared.utils.Constant.BASE_URL;

/**
 * @author Mamadou Bobo on 28/10/2022
 * @Project Tontine
 */

@RestController
@RequestMapping(value = BASE_URL+"/role")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @PutMapping("/give-role")
    public void giveRoleToUser(@RequestBody RoleDto roleDto) {
        roleService.giveRoleToUser(roleDto.getUsername(), roleDto.getName());
    }

}
