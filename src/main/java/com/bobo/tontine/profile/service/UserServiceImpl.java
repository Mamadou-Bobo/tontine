package com.bobo.tontine.profile.service;

import com.bobo.tontine.profile.entity.User;
import com.bobo.tontine.profile.repository.UserRepository;
import com.bobo.tontine.shared.exception.ResourceFoundException;
import com.bobo.tontine.shared.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Mamadou Bobo on 21/10/2022
 * @Project Tontine
 */

@RequiredArgsConstructor
@Slf4j
@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(() ->
                new ResourceNotFoundException("Username " + username + " not found in the database"));

        log.info("User found in the database: {}", username);
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }

    @Override
    public User addUser(User user) {
        log.info("adding new user {}", user.getUsername());

        if(userRepository.findByUsername(user.getUsername()).isPresent()) {
            log.error("Username {} already exist", user.getUsername());
            throw new ResourceFoundException("Username " + user.getUsername() + " already exist");
        }

        if(userRepository.findByEmail(user.getEmail()).isPresent()) {
            log.error("User with email {} already exist", user.getEmail());
            throw new ResourceFoundException("User with email " + user.getEmail() + " already exist");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public ResponseEntity<Object> updateUser(User user, Principal principal) {
        log.info("current user: {}", principal);
        log.info("updating user informations");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(!(authentication instanceof AnonymousAuthenticationToken)) {
            if(authentication.getName().equals(user.getUsername()) && userRepository.findByUsername(authentication.getName()).isPresent()) {
                User userInfo = userRepository.findByUsername(user.getUsername()).get();

                if(user.getUsername() != null && !user.getUsername().equals("")) {
                    userInfo.setUsername(user.getUsername());
                }

                if(user.getPassword() != null && !user.getPassword().equals("")) {
                    userInfo.setPassword(user.getPassword());
                }

                if(user.getEmail() != null && !user.getEmail().equals("")) {
                    userInfo.setEmail(user.getEmail());
                }

                if(user.getLastName() != null && !user.getLastName().equals("")) {
                    userInfo.setLastName(user.getLastName());
                }

                if(user.getFirstName() != null && !user.getFirstName().equals("")) {
                    userInfo.setFirstName(user.getFirstName());
                }

                if(user.getRoles() != null) {
                    userInfo.setRoles(user.getRoles());
                }

                userRepository.save(userInfo);

                return ResponseEntity.ok().body("user informations updated with success");
            }

            return new ResponseEntity<>("Username " + user.getUsername() + " does not exist",HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>("Authentication is required to access to this resource", HttpStatus.FORBIDDEN);
    }
}
