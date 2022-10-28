package com.bobo.tontine.profile.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.List;

/**
 * @author Mamadou Bobo on 21/10/2022
 * @Project Tontine
 */
@Entity
@Table(name = "user_tbl")
@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
        joinColumns = {
                @JoinColumn(name = "user_id")
        },
        inverseJoinColumns = {
                @JoinColumn(name = "role_id")
        }
    )
    private List<Role> roles;

}
