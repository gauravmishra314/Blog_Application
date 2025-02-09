package com.BlogApplication.Blog.services.impl;

import com.BlogApplication.Blog.exceptions.ResourceNotFoundException;
import com.BlogApplication.Blog.models.User;
import com.BlogApplication.Blog.payloads.UserDto;
import com.BlogApplication.Blog.repositories.UserRepo;
import com.BlogApplication.Blog.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModelMapper modelMapper;

    private User dtoToUser(UserDto userDto){
        User user = this.modelMapper.map(userDto , User.class);

//        user.setId(userDto.getId());
//        user.setName(userDto.getName());
//        user.setEmail(userDto.getEmail());
//        user.setPassword(userDto.getPassword());

        return user;
    }

    private UserDto userToDto(User user){
        UserDto userDto = this.modelMapper.map(user,UserDto.class);

//        userDto.setId(user.getId());
//        userDto.setName(user.getName());
//        userDto.setEmail(user.getEmail());
//        userDto.setPassword(user.getPassword());

        return userDto;
    }

    @Override
    public UserDto createUser(UserDto userDto){
        User user = this.dtoToUser((userDto));
        User savedUser = this.userRepo.save(user);
        return this.userToDto(savedUser);
    }

    @Override
    public UserDto updateUser(UserDto userDto, Integer userId){
        User user = this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User","id",userId));
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());

        User updatedUser = this.userRepo.save(user);
        return this.userToDto(updatedUser);
    }

//    @Override
//    public UserDto getUserById(Integer userId){
//        return null;
//    }

    @Override
    public List<UserDto> getAllUsers(){
        List<User> users = this.userRepo.findAll();
        List<UserDto> userDtos = users.stream().map(user->this.userToDto(user)).collect(Collectors.toList());
        return userDtos;
    }

//    public void deleteUser(Integer userId){
//
//    }
}
