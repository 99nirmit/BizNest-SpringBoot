package com.business.BizNest.service;

import com.business.BizNest.DTO.PermissionDto;
import com.business.BizNest.DTO.RoleDto;
import com.business.BizNest.DTO.UserDetailDTO;
import com.business.BizNest.domain.Permission;
import com.business.BizNest.domain.Role;
import com.business.BizNest.domain.User;
import com.business.BizNest.repository.PermissionRepository;
import com.business.BizNest.repository.RoleRepository;
import com.business.BizNest.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id){
        return userRepository.findById(id);
    }

    public UserDetailDTO createUser(User user, Set<String> roleName, Set<String> permissionName){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Set<Role> roles = getRoles(roleName);
        user.setRoles(roles);
        Set<Permission> permissions = getPermission(permissionName);
        user.setPermissions(permissions);
        userRepository.save(user);

        return toUserDetailDto(user);
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

    public Set<Role> getRoles(Set<String> roleNames){
        Set<Role> roles = new HashSet<>();
        for(String roleName : roleNames){
            Role role = roleRepository.findByRoleName(roleName)
                    .orElseThrow(() -> new RuntimeException("Role Not Found:" + roleName));
            roles.add(role);

        }
        return roles;
    }

    public Set<Permission> getPermission(Set<String> permissionNames){
        Set<Permission> permissions = new HashSet<>();
        for(String permissionName : permissionNames){
            Permission permission = permissionRepository.findByPermissionName(permissionName)
                    .orElseThrow(() -> new RuntimeException("Permission not Found: " + permissionName));
            permissions.add(permission);
        }
        return permissions;
    }

    private UserDetailDTO toUserDetailDto(User user){
        UserDetailDTO dto = new UserDetailDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setUsername(user.getUserName());
        dto.setPassword(user.getPassword());

        // Map Roles
        Set<RoleDto> roleDtos = user.getRoles().stream()
                .map(role -> new RoleDto(role.getRoleId(), role.getRoleName()))
                .collect(Collectors.toSet());
        dto.setRoles(roleDtos);

        //Map Permission

        Set<PermissionDto> permissionDtos = user.getPermissions().stream()
                .map(permission -> new PermissionDto(permission.getPermissionId(), permission.getPermissionName()))
                .collect(Collectors.toSet());
        dto.setPermissions(permissionDtos);

        return dto;
    }
}
