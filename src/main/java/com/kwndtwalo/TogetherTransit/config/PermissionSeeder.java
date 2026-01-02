package com.kwndtwalo.TogetherTransit.config;

/* Purpose of the class
* This class runs automatically at startup and inserts the required permission levels only if they don't already exist
* */

import com.kwndtwalo.TogetherTransit.domain.users.PermissionLevel;
import com.kwndtwalo.TogetherTransit.factory.users.PermissionLevelFactory;
import com.kwndtwalo.TogetherTransit.repository.users.PermissionLevelRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class PermissionSeeder {

    private final PermissionLevelRepository permissionLevelRepository;

    public PermissionSeeder(PermissionLevelRepository permissionLevelRepository) {
        this.permissionLevelRepository = permissionLevelRepository;
    }

    @PostConstruct
    public void initializePermissions() {

        seedPermission(
                PermissionLevel.PermissionType.SUPER_ADMIN,
                "Full system control",
                Set.of("ALL") //Super admin bypasses all checks
        );

        seedPermission(
                PermissionLevel.PermissionType.VERIFICATION_ADMIN,
                "Handles parent / driver account verification",
                Set.of(
                        "VIEW_PENDING_ACCOUNTS",
                        "APPROVE_PARENT",
                        "APPROVE_DRIVER",
                        "REJECT_PARENT",
                        "REJECT_DRIVER",
                        "VIEW_DOCUMENTS"
                )
        );

        seedPermission(
                PermissionLevel.PermissionType.SUPPORT_ADMIN,
                "Handles issues, messages, and help requests",
                Set.of(
                        "VIEW_SUPPORT_TICKETS",
                        "RESPOND_TO_PARENT",
                        "RESPOND_TO_DRIVER"
                )
        );

        seedPermission(
                PermissionLevel.PermissionType.FINANCE_ADMIN,
                "Manages payments, invoices and billing",
                Set.of(
                        "VIEW_PAYMENTS",
                        "GENERATE_INVOICES",
                        "VIEW_PARENT_BALANCE"
                )
        );

        System.out.println("Permission levels successfully seeded.");

    }
    private void seedPermission(PermissionLevel.PermissionType type,
                                String description,
                                Set<String> allowedActions) {

        //Ensure it doesn't duplicate records on each restart.
        if (permissionLevelRepository.findByPermissionType(type).isPresent()) {
            return;
        }

        PermissionLevel permission = PermissionLevelFactory.createPermissionLevel(
                type,
                description,
                allowedActions
        );

        permissionLevelRepository.save(permission);
    }
}
