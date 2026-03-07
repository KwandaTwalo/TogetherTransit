package com.kwndtwalo.TogetherTransit.controller.user;

import com.kwndtwalo.TogetherTransit.domain.users.Role;
import com.kwndtwalo.TogetherTransit.dto.users.RoleDTO;
import com.kwndtwalo.TogetherTransit.service.users.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/role")
public class RoleController {

    private final RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    /*
     * CREATE ROLE
     * -------------------------------------------------------
     * This endpoint allows creating a new role.
     * However, in the system:
     * - Roles are automatically inserted by RoleDataInitializer.
     * - This endpoint mainly exists for testing or admin control.
     */

    @PostMapping("/create")
    public ResponseEntity<RoleDTO> create(@RequestBody Role role) {
        Role createdRole = roleService.create(role);
        if (createdRole == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().body(new RoleDTO(createdRole));
    }

    /*Read Role
    * Fetches a role using its database ID
    * */
    @GetMapping("/read/{Id}")
    public ResponseEntity<RoleDTO> read(@PathVariable Long Id) {
        Role role = roleService.read(Id);
        if (role == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(new RoleDTO(role));
    }

    /*
     * GET ROLE BY NAME
     * -------------------------------------------------------
     * Fetches a role using its enum name:
     * ADMIN, DRIVER, or PARENT.
     */

    @GetMapping("/getByName/{roleName}")
    public ResponseEntity<RoleDTO> getByRoleName(@PathVariable Role.RoleName roleName) {
        Role role = roleService.getByRoleName(roleName);
        if (role == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(new RoleDTO(role));
    }

    /*
     * UPDATE ROLE
     * -------------------------------------------------------
     * Updates role information.
     */
    @PutMapping("/update")
    public ResponseEntity<RoleDTO> update(@RequestBody Role role) {
        Role updatedRole = roleService.update(role);
        if (updatedRole == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(new RoleDTO(updatedRole));
    }

    /*
     * GET ALL ROLES
     * -------------------------------------------------------
     * Returns a list of all system roles.
     * Usually returns: ADMIN, DRIVER, PARENT
     */
    @GetMapping("/getAll")
    public ResponseEntity<List<RoleDTO>> getAllRoles() {

        List<RoleDTO> roleDTOList = roleService.getAllRoles()
                .stream()
                .map(RoleDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(roleDTOList);
    }

    /*
     * DELETE ROLE BY ID
     * -------------------------------------------------------
     * Deletes a role using ID.
     * Normally this is dangerous in real systems.
     * Roles should usually NOT be deleted.
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Long id) {

        boolean deleted = roleService.delete(id);

        return ResponseEntity.ok(deleted);
    }

}
