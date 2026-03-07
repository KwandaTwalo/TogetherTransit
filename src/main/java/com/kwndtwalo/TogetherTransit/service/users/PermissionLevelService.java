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

    @Override
    public PermissionLevel create(PermissionLevel permissionLevel) {
        if (permissionLevel == null || permissionLevel.getPermissionType() == null) {
            return null;
        }

        //Prevent duplicate permission types (SUPER_ADMIN, etc)
        if (permissionLevelRepository
                .findByPermissionType(permissionLevel.getPermissionType())
                .isPresent()) {

            return permissionLevelRepository
                    .findByPermissionType(permissionLevel
                            .getPermissionType())
                    .orElse(null);
        }

        // Save only if it doesn't exist
        return permissionLevelRepository.save(permissionLevel);
    }

    @Override
    public PermissionLevel read(Long Id) {
        return permissionLevelRepository.findById(Id).orElse(null);
    }

    @Override
    public PermissionLevel update(PermissionLevel permissionLevel) {
        if (permissionLevel == null || permissionLevel.getPermissionId() == null) {
            return null;
        }

        if (!permissionLevelRepository.existsById(permissionLevel.getPermissionId())) {
            return null;
        }
        return permissionLevelRepository.save(permissionLevel);
    }

    @Override
    public List<PermissionLevel> getAllPermissionLevels() {
        return permissionLevelRepository.findAll();
    }

    @Override
    public PermissionLevel getByPermissionLevelType(PermissionLevel.PermissionType permissionLevelType) {

        if (permissionLevelType == null) {
            return null;
        }

        return permissionLevelRepository
                .findByPermissionType(permissionLevelType)
                .orElse(null);
    }

    @Override
    public boolean delete(Long Id) {
       if (!permissionLevelRepository.existsById(Id)) {
           return false;
       }
       permissionLevelRepository.deleteById(Id);
       return true;
    }
}
