package com.kwndtwalo.TogetherTransit.service.users;

import com.kwndtwalo.TogetherTransit.domain.users.PermissionLevel;
import com.kwndtwalo.TogetherTransit.repository.users.PermissionLevelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionLevelService implements IPermissionLevelService {

    private final PermissionLevelRepository permissionLevelRepository;

    @Autowired
    public PermissionLevelService(PermissionLevelRepository permissionLevelRepository) {
        this.permissionLevelRepository = permissionLevelRepository;
    }

    // =========================================================
    // BUSINESS RULE:
    // Permission levels are seeded and system-controlled.
    // Only SUPER_ADMIN should be able to create new ones.
    // =========================================================
    @Override
    public PermissionLevel create(PermissionLevel permissionLevel) {
        throw new UnsupportedOperationException(
                "Permission levels are system-controlled. Creation restricted to SUPER_ADMIN or Seeder.");
    }

    // =========================================================
    // BUSINESS RULE:
    // Read permission level safely by ID
    // =========================================================
    @Override
    public PermissionLevel read(Long id) {
        return permissionLevelRepository.findById(id).orElse(null);
    }

    // =========================================================
    // BUSINESS RULE:
    // Update only description and allowedActions.
    // PermissionType (enum) must never change.
    // =========================================================
    @Override
    public PermissionLevel update(PermissionLevel permissionLevel) {
        if (permissionLevel == null || permissionLevel.getPermissionId() == null) {
            return null;
        }

        PermissionLevel existing = permissionLevelRepository.findById(permissionLevel.getPermissionId())
                .orElse(null);
        if (existing == null) {
            return null;
        }

        //  Use Builder to preserve immutable fields (permissionType, id)
        PermissionLevel updated = new PermissionLevel.Builder()
                .copy(existing) // copy current state
                .setPermissionDescription(permissionLevel.getPermissionDescription()) // update description
                .setAllowedActions(permissionLevel.getAllowedActions()) // update allowed actions
                .build();

        return permissionLevelRepository.save(updated);
    }

    // =========================================================
    // BUSINESS RULE:
    // Return all permission levels (for listing in DTO responses)
    // =========================================================
    @Override
    public List<PermissionLevel> getAllPermissionLevels() {
        return permissionLevelRepository.findAll();
    }

    // =========================================================
    // BUSINESS RULE:
    // Fetch by PermissionType (enum)
    // =========================================================
    @Override
    public PermissionLevel getByPermissionLevelType(PermissionLevel.PermissionType permissionLevelType) {
        if (permissionLevelType == null) {
            return null;
        }
        return permissionLevelRepository.findByPermissionType(permissionLevelType).orElse(null);
    }

    // =========================================================
    // BUSINESS RULE:
    // Prevent deletion of seeded permission levels.
    // =========================================================
    @Override
    public boolean delete(Long id) {
        throw new UnsupportedOperationException(
                "Permission levels cannot be deleted. Restricted to SUPER_ADMIN.");
    }
}