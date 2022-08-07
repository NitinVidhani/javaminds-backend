package tech.javaminds.javaminds.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import tech.javaminds.javaminds.dto.*;
import tech.javaminds.javaminds.entity.Role;
import tech.javaminds.javaminds.entity.User;
import tech.javaminds.javaminds.service.RoleService;
import tech.javaminds.javaminds.service.UserService;
import tech.javaminds.javaminds.util.JwtUtil;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserService userService;


    @PostMapping("/authenticate")
    public ResponseEntity<?> generateToken(@RequestBody LoginUser loginUser) throws AuthenticationException {

        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginUser.getUsername(),
                        loginUser.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final String token = jwtUtil.generateToken(authentication);

        User user = userService.findOne(loginUser.getUsername());

        LoginResponse response = new LoginResponse();
        response.setUsername(user.getUsername());
        response.setAuthToken(token);
        Set<String> roles = new HashSet<>();
        user.getRoles().forEach((role) -> roles.add(role.getName()));
        response.setRoles(roles);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public List<User> getUsers() {
        return userService.findAll();
    }

    @PreAuthorize("hasRole('Admin')")
    @PostMapping("/register")
    public User saveUser(@RequestBody UserDto user) {
        return userService.save(user);
    }


    @PreAuthorize("hasRole('Admin')")
    @PutMapping("/{userId}")
    public User updateUser(@PathVariable("userId") long id, @RequestBody User user) {
        user.setId(id);
        return userService.updateUser(user);
    }

    @PreAuthorize("hasRole('Admin')")
    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable("userId") long id) {
        userService.deleteUser(id);
    }

    @PreAuthorize("hasRole('User')")
    @GetMapping("/userping")
    public String userPing(){
        return "Any User Can Read This";
    }

    @PreAuthorize("hasRole('Admin')")
    @GetMapping("/adminping")
    public String adminPing(){
        return "Only admin User Can Read This";
    }

}
