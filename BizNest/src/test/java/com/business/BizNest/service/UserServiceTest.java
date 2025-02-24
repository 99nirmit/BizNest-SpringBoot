package com.business.BizNest.service;

import com.business.BizNest.domain.Permission;
import com.business.BizNest.domain.Role;
import com.business.BizNest.domain.User;
import com.business.BizNest.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.HashSet;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

//    create mock roles
    Role adminRole = new Role(1L, "ADMIN");
    Role UserRole = new Role(2L, "USER");

    Set<Role> roles = new HashSet<>();
    Set<Permission> permissions = new HashSet<>();

//    create mock permissions
    Permission readPermissions = new Permission(1L, "READ");

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void getAllUsers() {
//        Arrange
        roles.add(adminRole);
        permissions.add(readPermissions);

        User user = new User(1L, "Nirmit", "nirmit_99", "nirmit@gmail.com", "1234", "Nirmit@99", roles, permissions);

        assertEquals(1, user.getId());
        assertEquals("Nirmit", user.getName());
    }

    @Test
    void getUserById() {
        //        Arrange
        roles.add(adminRole);
        permissions.add(readPermissions);


        User user = new User(2L, "Nirmit", "nirmit_99", "nirmit@gmail.com", "1234", "Nirmit@99", roles, permissions);

        //Additional assertions (optional)
        assertNotNull(user);
        assertEquals("Nirmit", user.getName());
        assertNotEquals(2, user.getId());
        assertTrue(user.getRoles().contains(adminRole));
    }

    @Test
    void createUser() {
    }

    @Test
    void updateUser() {
    }

    @Test
    void deleteById() {
    }

    @Test
    void getRoles() {
    }

    @Test
    void getPermission() {
    }
}