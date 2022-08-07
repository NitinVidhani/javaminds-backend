package tech.javaminds.javaminds.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tech.javaminds.javaminds.dto.RoleDto;
import tech.javaminds.javaminds.entity.Role;
import tech.javaminds.javaminds.exception.ResourceAlreadyExistsException;
import tech.javaminds.javaminds.service.RoleService;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/roles")
public class RoleController {

    @Autowired
    private RoleService roleService;

    // This may not be needed in the future
    @PreAuthorize("hasRole('Admin')")
    @PostMapping
    public Role createNewRole(@RequestBody RoleDto role) {
        return roleService.createNewRole(role);
    }


    @GetMapping("/{name}")
    public Role getRole(@PathVariable("name") String name) {
        return roleService.findByName(name);
    }

    @GetMapping
    public List<Role> getAllRoles() {
        return roleService.getAllRoles();
    }

}
