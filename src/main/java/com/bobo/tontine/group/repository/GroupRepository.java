package com.bobo.tontine.group.repository;

import com.bobo.tontine.group.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Mamadou Bobo on 28/10/2022
 * @Project Tontine
 */

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {
    Optional<Group> findByName(String name);
}
