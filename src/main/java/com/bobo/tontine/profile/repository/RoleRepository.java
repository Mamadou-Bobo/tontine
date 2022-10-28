package com.bobo.tontine.profile.repository;

import com.bobo.tontine.profile.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Mamadou Bobo on 21/10/2022
 * @Project Tontine
 */

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {
    Optional<Role> findByName(String name);
}
