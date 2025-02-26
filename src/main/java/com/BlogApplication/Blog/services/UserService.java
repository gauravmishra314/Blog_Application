package com.BlogApplication.Blog.services;

import com.BlogApplication.Blog.payloads.UserDto;

import java.util.List;

public interface UserService {
    UserDto updateUser(UserDto user, Integer userId);
    //UserDto getUserById(Integer userId);
    List<UserDto> getAllUsers();

    void createUser(UserDto userDto);
    //void deleteUser(Integer userId);
}
