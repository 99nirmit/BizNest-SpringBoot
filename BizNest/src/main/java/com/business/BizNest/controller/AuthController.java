package com.business.BizNest.controller;

import com.business.BizNest.DTO.ApiResponse;
import com.business.BizNest.DTO.LoginRequestDto;
import com.business.BizNest.DTO.LoginResponseDto;
import com.business.BizNest.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;

@RestController
@RequestMapping("api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponseDto>> authenticateUser(@RequestBody LoginRequestDto loginRequestDto) throws AuthenticationException {

        try{
            LoginResponseDto response = authService.authenticateUser(loginRequestDto);
            return ResponseEntity.ok(new ApiResponse<>(true, response, "Login Successfully"));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse<>(false, null, "Bad Credentials"));
        }

    }
}
