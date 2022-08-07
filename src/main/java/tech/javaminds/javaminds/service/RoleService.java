package tech.javaminds.javaminds.service;

import tech.javaminds.javaminds.dto.RoleDto;
import tech.javaminds.javaminds.entity.Role;

import java.util.List;

public interface RoleService {

    Role findByName(String name);

    Role createNewRole(RoleDto role);

    List<Role> getAllRoles();
}
