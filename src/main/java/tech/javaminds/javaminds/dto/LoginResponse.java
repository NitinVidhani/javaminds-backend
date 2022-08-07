package tech.javaminds.javaminds.dto;

import java.util.Set;

public class LoginResponse {

    private String username;
    private Set<String> roles;
    private String authToken;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    @Override
    public String toString() {
        return "LoginResponse{" +
                "username='" + username + '\'' +
                ", roles=" + roles +
                ", authToken='" + authToken + '\'' +
                '}';
    }
}
