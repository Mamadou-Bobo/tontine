package com.bobo.tontine.profile.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

/**
 * @author Mamadou Bobo on 21/10/2022
 * @Project Tontine
 */
@Entity
@Table(name = "role_tbl")
@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
}
