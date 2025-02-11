package com.business.BizNest.DTO;

import com.business.BizNest.domain.Permission;
import lombok.Data;

import java.util.Set;

@Data
public class UserCreationRequestDto {

    private Long id;
    private String name;
    private String username;
    private String email;
    private String phoneNumber;
    private String password;
    private Set<String> rolesNames;
    private Set<String> permissionsNames;
}
