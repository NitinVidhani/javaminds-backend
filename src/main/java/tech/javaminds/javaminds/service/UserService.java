package tech.javaminds.javaminds.service;

import tech.javaminds.javaminds.dto.UserDto;
import tech.javaminds.javaminds.entity.User;

import java.util.List;

public interface UserService {

    User save(UserDto user);

    User createAdmin(User user);

    List<User> findAll();

    User updateUser(User user);

    void deleteUser(long id);

    User findOne(String username);

}
