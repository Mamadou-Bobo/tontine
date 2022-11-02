package com.bobo.tontine.group.repository;

import com.bobo.tontine.group.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Mamadou Bobo on 28/10/2022
 * @Project Tontine
 */

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {
    Optional<Group> findByName(String name);

    @Query(value = "select * from group_tbl where created_By = (select id from user_tbl where username = :groupCreatorUsername)", nativeQuery = true)
    Group findGroupCreatorByUsername(String groupCreatorUsername);
}
