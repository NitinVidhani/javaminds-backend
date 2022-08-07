package tech.javaminds.javaminds.dto;

import tech.javaminds.javaminds.entity.Role;

public class RoleDto {

    private String name;
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "RoleDto{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    public Role getRoleFromDto() {
        Role role = new Role();
        role.setName(name);
        role.setDescription(description);
        return role;
    }
}
