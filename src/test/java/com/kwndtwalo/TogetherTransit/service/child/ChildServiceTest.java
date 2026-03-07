package com.kwndtwalo.TogetherTransit.service.child;

import com.kwndtwalo.TogetherTransit.domain.child.Child;
import com.kwndtwalo.TogetherTransit.domain.users.Parent;
import com.kwndtwalo.TogetherTransit.domain.users.Role;
import com.kwndtwalo.TogetherTransit.domain.users.User;
import com.kwndtwalo.TogetherTransit.dto.child.ChildDTO;
import com.kwndtwalo.TogetherTransit.factory.auth.AuthenticationFactory;
import com.kwndtwalo.TogetherTransit.factory.child.ChildFactory;
import com.kwndtwalo.TogetherTransit.factory.generic.AddressFactory;
import com.kwndtwalo.TogetherTransit.factory.generic.ContactFactory;
import com.kwndtwalo.TogetherTransit.factory.users.ParentFactory;
import com.kwndtwalo.TogetherTransit.service.users.ParentService;
import com.kwndtwalo.TogetherTransit.service.users.RoleService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.MethodName.class)
class ChildServiceTest {

    @Autowired
    private ChildService childService;

    @Autowired
    private ParentService parentService;

    @Autowired
    private RoleService roleService;

    private static Child savedChild;

    @Test
    void a_create() {

        Role role = roleService.getByRoleName(Role.RoleName.PARENT);

        Parent parent = ParentFactory.createParent(
                "Sibongile",
                "Twalo",
                LocalDate.of(1985, 3, 5),
                User.AccountStatus.ACTIVE,
                ContactFactory.createContact("0721111111", "0822222222"),
                AddressFactory.createAddress("17", "Long Street", "CBD", "Cape Town", 8000),
                AuthenticationFactory.createAuthentication(
                        "parent@yahoo.com",
                        "StrongPassword123@!",
                        LocalDateTime.now(),
                        false
                ),
                role
        );

        Parent savedParent = parentService.create(parent);

        Child child = ChildFactory.createChild(
                "Ayanda",
                "Twalo",
                LocalDate.of(2014, 3, 5),
                "grade 5",
                savedParent
        );

        savedChild = childService.create(child);

        assertNotNull(savedChild);
        System.out.println(" Child created successfully:");
        System.out.println(new ChildDTO(savedChild));
    }

    @Test
    void b_read() {
        Child found = childService.read(savedChild.getChildId());
        assertNotNull(found);

        System.out.println(" Child read:");
        System.out.println(new ChildDTO(found));
    }

    @Test
    void c_update() {
        Child updated = new Child.Builder()
                .copy(savedChild)
                .setGrade("grade 6")
                .build();

        Child result = childService.update(updated);
        assertNotNull(result);

        System.out.println(" Child updated:");
        System.out.println(new ChildDTO(result));
    }

    @Test
    void d_getAll() {
        List<Child> children = childService.getAllChildren();
        assertNotNull(children);
        assertFalse(children.isEmpty());
        System.out.println(" All children fetched successfully: ");
    }

    @Test
    void e_delete() {
        boolean deleted = childService.delete(savedChild.getChildId());
        assertTrue(deleted);

        System.out.println(" Child deleted successfully");
    }
}