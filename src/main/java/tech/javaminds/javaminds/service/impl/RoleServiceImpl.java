package tech.javaminds.javaminds.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.javaminds.javaminds.dto.RoleDto;
import tech.javaminds.javaminds.entity.Role;
import tech.javaminds.javaminds.exception.ResourceAlreadyExistsException;
import tech.javaminds.javaminds.exception.ResourceNotFoundException;
import tech.javaminds.javaminds.repository.RoleRepository;
import tech.javaminds.javaminds.service.RoleService;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Role findByName(String name) {
        Role role = roleRepository.findRoleByName(name);
        if(role == null) {
            throw new ResourceNotFoundException("Role with name: " + name + " not found");
        }
        return role;
    }


    // It may not be needed in future
    // For testing purposes only
    @Override
    public Role createNewRole(RoleDto role) {
        Role existedRole = roleRepository.findRoleByName(role.getName());
        if(existedRole != null) {
            throw new ResourceAlreadyExistsException("Role with name: "+ role.getName() + " already exists");
        }
        Role nRole = role.getRoleFromDto();
        return roleRepository.save(nRole);
    }

    @Override
    public List<Role> getAllRoles() {
        List<Role> list = new ArrayList<>();
        roleRepository.findAll().iterator().forEachRemaining(list::add);
        return list;
    }
}
