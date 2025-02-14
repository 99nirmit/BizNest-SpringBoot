package com.business.BizNest.DTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponseDto {

    private String jwtToken;
    private String userName;
    private List<String> authorities;

    public LoginResponseDto(String username, List<String> authorities, String jwtToken) {
        this.jwtToken = jwtToken;
        this.authorities = authorities;
        this.userName = username;
    }
}
