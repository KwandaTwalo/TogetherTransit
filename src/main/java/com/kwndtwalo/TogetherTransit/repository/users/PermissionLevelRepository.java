package com.kwndtwalo.TogetherTransit.repository.users;

import com.kwndtwalo.TogetherTransit.domain.users.PermissionLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PermissionLevelRepository extends JpaRepository<PermissionLevel, Long> {

    Optional<PermissionLevel> findByPermissionType(PermissionLevel.PermissionType permissionType);
}
