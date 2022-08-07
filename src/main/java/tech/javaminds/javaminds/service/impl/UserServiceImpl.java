package tech.javaminds.javaminds.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tech.javaminds.javaminds.dto.UserDto;
import tech.javaminds.javaminds.entity.Role;
import tech.javaminds.javaminds.entity.User;
import tech.javaminds.javaminds.exception.ResourceAlreadyExistsException;
import tech.javaminds.javaminds.exception.ResourceNotFoundException;
import tech.javaminds.javaminds.repository.UserRepository;
import tech.javaminds.javaminds.service.RoleService;
import tech.javaminds.javaminds.service.UserService;

import java.util.*;

@Service(value = "userService")
public class UserServiceImpl implements UserDetailsService, UserService {

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if(user == null) {
            throw new UsernameNotFoundException("invalid username or password");
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), getAuthority(user));
    }

    private Set<SimpleGrantedAuthority> getAuthority(User user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
        });
        return authorities;
    }

    @Override
    public User save(UserDto user) {

        User existingUser = userRepository.findByUsername(user.getUsername());
        if(existingUser != null) {
            throw new ResourceAlreadyExistsException("User with username: " + user.getUsername() + " already exists");
        }

        User nUser = user.getUserFromDto();
        nUser.setPassword(passwordEncoder.encode(user.getPassword()));

        Role role = roleService.findByName("USER");
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(role);

        nUser.setRoles(roleSet);

        return userRepository.save(nUser);
    }

    @Override
    public User createAdmin(User user) {
        User existingUser = userRepository.findByUsername(user.getUsername());
        if(existingUser != null) {
            throw new ResourceAlreadyExistsException("User with username: " + user.getUsername() + " already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public List<User> findAll() {
        List<User> list = new ArrayList<>();
        userRepository.findAll().iterator().forEachRemaining(list::add);
        return list;
    }

    @Override
    public User updateUser(User user) {
        if(userRepository.findById(user.getId()).isPresent()) {
            return userRepository.save(user);
        }
        return null;
    }

    @Override
    public User findOne(String username) {
        User user = userRepository.findByUsername(username);
        if(user == null) {
            throw new ResourceNotFoundException("User with username: " + username + " not found");
        }
        return user;
    }

    @Override
    public void deleteUser(long id) {
        if(userRepository.findById(id).isPresent()) {
            Set<Role> roles = userRepository.findById(id).get().getRoles();
            for(Role role: roles) {
                if(role.getName().equals("Admin")) {
                    return;
                }
            }
            userRepository.delete(userRepository.findById(id).get());
        }

    }
}
