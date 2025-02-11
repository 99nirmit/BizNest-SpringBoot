package com.business.BizNest.controller;

import com.business.BizNest.DTO.UserCreationRequestDto;
import com.business.BizNest.DTO.UserDetailDTO;
import com.business.BizNest.domain.User;
import com.business.BizNest.repository.UserRepository;
import com.business.BizNest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public List<User> getALlUser(){
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public Optional<User> getUserById(@PathVariable Long id){
        return userService.getUserById(id);
    }

    @PostMapping("/create")
    public ResponseEntity<UserDetailDTO> createUser(@RequestBody UserCreationRequestDto request){
        User user = new User();
        user.setName(request.getName());
        user.setUserName(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setPhoneNumber(request.getPhoneNumber());

        UserDetailDTO createdUser = userService.createUser(user, request.getRolesNames(), request.getPermissionsNames());

        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteUser(@PathVariable Long id){
        userService.deleteById(id);
    }

}
