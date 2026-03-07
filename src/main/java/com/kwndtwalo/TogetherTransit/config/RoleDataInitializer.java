package com.kwndtwalo.TogetherTransit.config;

import com.kwndtwalo.TogetherTransit.domain.users.Role;
import com.kwndtwalo.TogetherTransit.factory.users.RoleFactory;
import com.kwndtwalo.TogetherTransit.service.users.RoleService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*This class loads the 3 Roles in the Database everytime the system starts running.
* This helps to prevent duplicates so instead of creating new role everytime we just fetch these loaded role*/

@Configuration
public class RoleDataInitializer {

    @Bean
    CommandLineRunner loadRoles(RoleService roleService) {
        return args -> {
            roleService.create(RoleFactory.createRole(Role.RoleName.PARENT));
            roleService.create(RoleFactory.createRole(Role.RoleName.DRIVER));
            roleService.create(RoleFactory.createRole(Role.RoleName.ADMIN));

            System.out.println("System roles loaded");
        };
    }
}
