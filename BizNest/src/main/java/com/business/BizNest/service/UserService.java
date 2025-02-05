package com.business.BizNest.service;

import com.business.BizNest.domain.User;
import com.business.BizNest.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id){
        return userRepository.findById(id);
    }

    public User createUser(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(user.getRoles());
        user.setPermissions(user.getPermissions());
        return userRepository.save(user);
    }

    public User updateUser(User user){

        User updtaedUser = userRepository.findByUserName(user.getUserName());
        updtaedUser.setEmail(user.getEmail());
        updtaedUser.setPassword(user.getPassword());
        updtaedUser.setName(user.getName());
        updtaedUser.setRoles(user.getRoles());
        updtaedUser.setPermissions(user.getPermissions());

        return userRepository.save(updtaedUser);
    }

    public void deleteById(Long id){
        userRepository.deleteById(id);
    }
}
