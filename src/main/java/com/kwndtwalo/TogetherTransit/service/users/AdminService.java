package com.kwndtwalo.TogetherTransit.service.users;

// import com.kwndtwalo.TogetherTransit.domain.auth.Authentication;
// import com.kwndtwalo.TogetherTransit.domain.generic.Address;
// import com.kwndtwalo.TogetherTransit.domain.generic.Contact;
import com.kwndtwalo.TogetherTransit.domain.users.Admin;
import com.kwndtwalo.TogetherTransit.domain.users.Driver;
import com.kwndtwalo.TogetherTransit.domain.users.Parent;
import com.kwndtwalo.TogetherTransit.domain.users.PermissionLevel;
import com.kwndtwalo.TogetherTransit.domain.users.Role;
import com.kwndtwalo.TogetherTransit.domain.users.User;
// import com.kwndtwalo.TogetherTransit.factory.users.AdminFactory;
import com.kwndtwalo.TogetherTransit.repository.users.IAdminRepository;
import com.kwndtwalo.TogetherTransit.repository.users.IDriverRepository;
import com.kwndtwalo.TogetherTransit.repository.users.IParentRepository;
import com.kwndtwalo.TogetherTransit.repository.users.IRoleRepository;
import com.kwndtwalo.TogetherTransit.repository.users.PermissionLevelRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AdminService implements IAdminService {

    private IAdminRepository adminRepository;
    private IRoleRepository roleRepository;
    private IDriverRepository driverRepository;
    private IParentRepository parentRepository;
    private PermissionLevelRepository permissionLevelRepository;

    @Autowired
    public AdminService(IAdminRepository adminRepository, IRoleRepository roleRepository,
            IDriverRepository driverRepository, IParentRepository parentRepository,
            PermissionLevelRepository permissionLevelRepository) {
        this.adminRepository = adminRepository;
        this.roleRepository = roleRepository;
        this.driverRepository = driverRepository;
        this.parentRepository = parentRepository;
        this.permissionLevelRepository = permissionLevelRepository;
    }

    /*
     * Create or return existing Admin.
     * Business rule:
     * Same first name + last name = same Admin (duplicate prevention).
     */
    @Override
    public Admin create(Admin admin) {
        if (admin == null) {
            return null;
        }

        // Prevent duplicates by name
        return adminRepository
                .findByFirstNameAndLastName(admin.getFirstName(), admin.getLastName())
                .orElseGet(() -> {
                    // Fetch persisted ADMIN role from DB
                    Role enforcedRole = roleRepository.findByRoleName(Role.RoleName.ADMIN)
                            .orElseThrow(() -> new IllegalStateException("Default ADMIN role not found in database"));

                    // Lookup existing PermissionLevel by type
                    if (admin.getPermissionLevel() == null || admin.getPermissionLevel().getPermissionType() == null) {
                        throw new IllegalStateException("Admin must have a valid permission type");
                    }

                    PermissionLevel existingPermissionLevel = permissionLevelRepository
                            .findByPermissionType(admin.getPermissionLevel().getPermissionType())
                            .orElseThrow(() -> new IllegalStateException("Permission level not found in database"));

                    // Set system-managed defaults
                    Admin newAdmin = new Admin.Builder()
                            .copy(admin)
                            .setRole(enforcedRole)
                            .setPermissionLevel(existingPermissionLevel)
                            .setCreatedAt(LocalDate.now()) // set creation date
                            .setAccountStatus(User.AccountStatus.ACTIVE) // default status
                            // lastLogin intentionally left null until first login
                            .build();

                    return adminRepository.save(newAdmin);
                });
    }

    @Override
    public Admin read(Long Id) {
        return adminRepository.findById(Id).orElse(null);
    }

    @Override
    public Admin update(Admin admin) {
        if (admin == null || admin.getUserId() == null) {
            return null;
        }

        Admin existing = adminRepository.findById(admin.getUserId()).orElse(null);
        if (existing == null) {
            return null;
        }

        // Use Builder to safely update profile fields
        // Notes:
        // - We copy the existing admin to preserve role and permission level.
        // - Only profile-related fields (name, contact, address, authentication) are
        // updated.
        Admin updated = new Admin.Builder()
                .copy(existing) // start from existing admin
                .setFirstName(admin.getFirstName() != null ? admin.getFirstName() : existing.getFirstName()) // update
                                                                                                             // profile
                                                                                                             // info
                .setLastName(admin.getLastName() != null ? admin.getLastName() : existing.getLastName())
                .setContact(admin.getContact() != null ? admin.getContact() : existing.getContact())
                .setAddress(admin.getAddress() != null ? admin.getAddress() : existing.getAddress())
                .setAuthentication(
                        admin.getAuthentication() != null ? admin.getAuthentication() : existing.getAuthentication())
                .setRole(existing.getRole())
                .setPermissionLevel(existing.getPermissionLevel())
                .build();

        return adminRepository.save(updated);
    }

    @Override
    public List<Admin> getAllAdmins() {
        return adminRepository.findAll();
    }

    @Override
    public boolean delete(Long Id) {
        if (!adminRepository.existsById(Id)) {
            return false;
        }
        adminRepository.deleteById(Id);
        return true;
    }
   

    // =========================================================
    // BUSINESS RULE: Change Admin Permission Level
    // - Restricted to SUPER_ADMIN
    // - Uses AdminPermissionDTO (newPermissionType)
    // =========================================================
    public Admin changePermission(Admin admin, String newPermissionType) {
        if (admin == null || newPermissionType == null) {
            return null;
        }

        // Only SUPER_ADMIN can change permissions
        if (admin.getPermissionLevel() == null ||
                admin.getPermissionLevel().getPermissionType() != PermissionLevel.PermissionType.SUPER_ADMIN) {
            throw new SecurityException("Only SUPER_ADMIN can change permissions");
        }

        // Convert string to enum safely
        PermissionLevel.PermissionType type;
        try {
            type = PermissionLevel.PermissionType.valueOf(newPermissionType);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid permission type: " + newPermissionType);
        }

        // Build updated Admin with new permission level
        Admin updated = new Admin.Builder()
                .copy(admin)
                .setPermissionLevel(
                        new PermissionLevel.Builder()
                                .copy(admin.getPermissionLevel())
                                .setPermissionType(type)
                                .build())
                .build();

        return adminRepository.save(updated);
    }

    // =========================================================
    // BUSINESS RULE: Approve Driver
    // - AllowedAction: "APPROVE_DRIVER"
    // - Transition: UNDER_REVIEW → ACTIVE
    // =========================================================
    public void approveDriver(Long driverId, Admin admin) {
        if (!admin.getPermissionLevel().getAllowedActions().contains("APPROVE_DRIVER")
                || admin.getPermissionLevel().getAllowedActions().contains("ALL")) {
            throw new SecurityException("Not authorized to approve drivers");
        }

        Driver driver = driverRepository.findById(driverId)
                .orElseThrow(() -> new IllegalArgumentException("Driver not found"));

        Driver updated = new Driver.Builder()
                .copy(driver)
                .setAccountStatus(User.AccountStatus.ACTIVE) // approved drivers become ACTIVE
                .build();

        driverRepository.save(updated);
    }

    // =========================================================
    // BUSINESS RULE: Reject Driver
    // - AllowedAction: "REJECT_DRIVER"
    // - Transition: UNDER_REVIEW → REJECTED
    // =========================================================
    public void rejectDriver(Long driverId, Admin admin) {
        if (!admin.getPermissionLevel().getAllowedActions().contains("REJECT_DRIVER")
                || admin.getPermissionLevel().getAllowedActions().contains("ALL")) {
            throw new SecurityException("Not authorized to reject drivers");
        }

        Driver driver = driverRepository.findById(driverId)
                .orElseThrow(() -> new IllegalArgumentException("Driver not found"));

        Driver updated = new Driver.Builder()
                .copy(driver)
                .setAccountStatus(User.AccountStatus.REJECTED) // rejected drivers become REJECTED
                .build();

        driverRepository.save(updated);
    }

    // =========================================================
    // BUSINESS RULE: Suspend User (Driver or Parent)
    // - AllowedAction: "SUSPEND_USER"
    // - Transition: ACTIVE → SUSPENDED
    // =========================================================
    public void suspendUser(User user, Admin admin) {
        if (!admin.getPermissionLevel().getAllowedActions().contains("SUSPEND_USER")
                || admin.getPermissionLevel().getAllowedActions().contains("ALL")) {
            throw new SecurityException("Not authorized to suspend users");
        }

        if (user instanceof Driver) {
            Driver updated = new Driver.Builder()
                    .copy((Driver) user)
                    .setAccountStatus(User.AccountStatus.SUSPENDED)
                    .build();
            driverRepository.save(updated);
        } else if (user instanceof Parent) {
            Parent updated = new Parent.Builder()
                    .copy((Parent) user)
                    .setAccountStatus(User.AccountStatus.SUSPENDED)
                    .build();
            parentRepository.save(updated);
        } else {
            throw new IllegalArgumentException("Unsupported user type for suspension");
        }
    }
    
}

 // // =========================================================
    // // BUSINESS RULE:
    // // Attach relationships AFTER Admin creation
    // // (THIS is where your Contact/Address/Auth/Role belong)
    // // =========================================================

    // public Admin attachRelations(Admin admin,
    // Contact contact,
    // Address address,
    // Authentication authentication,
    // Role role,
    // PermissionLevel permissionLevel) {

    // if (admin == null) {
    // return null;
    // }

    // Admin updated = new Admin.Builder()
    // .copy(admin)
    // .setContact(contact)
    // .setAddress(address)
    // .setAuthentication(authentication)
    // .setRole(role)
    // .build();

    // return adminRepository.save(updated);
    // }

    // public Admin registerAdmin(String firstName,
    // String lastName,
    // User.AccountStatus accountStatus,
    // Contact contact,
    // Address address,
    // Authentication authentication,
    // Role role,
    // PermissionLevel permissionLevel) {

    // // Enfroce defaults.
    // User.AccountStatus enforcedStatus = User.AccountStatus.ACTIVE; // default
    // status for new admins

    // // Fetch persisted ADMIN role from DB
    // Role enforcedRole = roleRepository.findByRoleName(Role.RoleName.ADMIN)
    // .orElseThrow(() -> new IllegalStateException("Default ADMIN role not found in
    // database"));

    // Admin admin = AdminFactory.createAdmin(
    // null, // lastLogin is null until first login
    // firstName,
    // lastName,
    // java.time.LocalDate.now(),
    // enforcedStatus // enforce default status

    // );

    // if (admin == null) {
    // return null;
    // }

    // Admin enriched = attachRelations(admin, contact, address, authentication,
    // enforcedRole, permissionLevel);

    // return adminRepository.save(enriched);

    // }
