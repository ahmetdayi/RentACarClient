package com.yunusahmet.rentacar.controller;



import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.yunusahmet.rentacar.business.AuthManager;
import com.yunusahmet.rentacar.dto.LoginRequest;
import com.yunusahmet.rentacar.dto.LoginResponse;

import javax.validation.Valid;

@RequestMapping("/login")
@RestController
@CrossOrigin(origins = "*")
public class AuthController {

    private final AuthManager authManager;

    public AuthController(AuthManager authManager) {
        this.authManager = authManager;
    }

    @PostMapping
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request){
        return new ResponseEntity<>(authManager.login(request), HttpStatus.OK);
    }
}
