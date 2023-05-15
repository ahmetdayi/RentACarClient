package com.yunusahmet.rentacar.business;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.yunusahmet.rentacar.core.security.JwtUtil;
import com.yunusahmet.rentacar.dto.LoginRequest;
import com.yunusahmet.rentacar.dto.LoginResponse;
import com.yunusahmet.rentacar.dto.converter.LoginResponseConverter;

@Service
@RequiredArgsConstructor
public class AuthManager {

    private final AuthenticationManager authenticationManager;

    private final CustomerManager customerManager;
    private final JwtUtil jwtUtil;

    private final LoginResponseConverter converter;



    public LoginResponse login(LoginRequest request){
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken
                (request.getEmail(),
                        request.getPassword());
        Authentication authenticate = authenticationManager.authenticate(token);
        System.out.println(authenticate.getName());
        int customerId= customerManager.findCustomerByEmail(authenticate.getName()).getCustomerId();

        return converter.convert(jwtUtil.generateToken(authenticate),customerId);
    }
}
