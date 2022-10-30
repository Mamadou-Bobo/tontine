package com.bobo.tontine;

import com.bobo.tontine.group.entity.Group;
import com.bobo.tontine.group.service.GroupService;
import com.bobo.tontine.profile.entity.Role;
import com.bobo.tontine.profile.entity.User;
import com.bobo.tontine.profile.service.RoleService;
import com.bobo.tontine.profile.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.Principal;
import java.util.ArrayList;

import static com.bobo.tontine.shared.utils.Constant.ROLE_ADMIN;
import static com.bobo.tontine.shared.utils.Constant.ROLE_CONTRIBUTOR;

@SpringBootApplication
public class TontineApplication {

    public static void main(String[] args) {
        SpringApplication.run(TontineApplication.class, args);
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    CommandLineRunner run(UserService userService, RoleService roleService, GroupService groupService) {
        return args -> {
            roleService.addRole(new Role(null, ROLE_ADMIN));
            roleService.addRole(new Role(null, ROLE_CONTRIBUTOR));

            userService.addUser(new User(null,
                    "john",
                    "John",
                    "Doe",
                    "john@moulinette.com",
                    "1234",
                    new ArrayList<>(),
                    new ArrayList<>()));

            roleService.giveRoleToUser("john",ROLE_ADMIN);

        };
    }

}
