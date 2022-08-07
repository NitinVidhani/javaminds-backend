package tech.javaminds.javaminds.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tech.javaminds.javaminds.dto.RoleDto;
import tech.javaminds.javaminds.entity.Role;
import tech.javaminds.javaminds.entity.User;
import tech.javaminds.javaminds.exception.ResourceNotFoundException;
import tech.javaminds.javaminds.service.RoleService;
import tech.javaminds.javaminds.service.UserService;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

@Component
public class InitComponents {

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserService userService;

    @PostConstruct
    public void initRolesAndUser() {
        RoleDto role1 = new RoleDto();
        role1.setName("Admin");
        role1.setDescription("Role for Admin");
        try {
            roleService.findByName("Admin");
        } catch (ResourceNotFoundException ex) {
            roleService.createNewRole(role1);
        }

        RoleDto role2 = new RoleDto();
        role2.setName("User");
        role2.setDescription("Role for normal users");
        try {
            roleService.findByName("User");
        } catch (ResourceNotFoundException ex) {
            roleService.createNewRole(role2);
        }

        User user = new User();
        user.setEmail("admin@gmail.com");
        Set<Role> roles = new HashSet<>();
        roles.add(roleService.findByName("Admin"));
        user.setRoles(roles);
        user.setPassword("Admin@123");
        user.setUsername("Admin");
        user.setName("Admin");
        user.setPhone("7869555400");

        try {
            userService.findOne(user.getUsername());
        } catch (ResourceNotFoundException ex) {
            userService.createAdmin(user);
        }

    }

}
