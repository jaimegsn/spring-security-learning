package com.example.basicauthimplementation.v1.service;

import com.example.basicauthimplementation.entity.Users;
import com.example.basicauthimplementation.v1.repository.UserRepository;
import com.example.basicauthimplementation.v1.dto.LoginDTO;
import com.example.basicauthimplementation.v1.dto.RegisterDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthService {

    private final PasswordEncoder encoder;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;

    private SecurityContextRepository securityContextRepository = new HttpSessionSecurityContextRepository();
    private SecurityContextHolderStrategy securityContextHolderStrategy = SecurityContextHolder.getContextHolderStrategy();

    @Autowired
    public AuthService(PasswordEncoder encoder,
                       UserRepository userRepository,
                       AuthenticationManager authenticationManager){

        this.encoder = encoder;
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
    }

    public Boolean register(RegisterDTO registerDTO){
        Users user = new Users(registerDTO.getUsername(),
                        registerDTO.getFirstName(),
                        registerDTO.getLastName(),
                        registerDTO.getEmail(),
                        encoder.encode(registerDTO.getPassword()),
                        registerDTO.getRoles());

        userRepository.save(user);
        return true;
    }

    public List<Users> showAllUsers(){
        return userRepository.findAll();
    }

    public Boolean login(LoginDTO loginDTO, HttpServletRequest request, HttpServletResponse response) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                loginDTO.getUsername(),
                loginDTO.getPassword());

        Authentication authentication = authenticationManager.authenticate(token);

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);
        securityContextRepository.saveContext(context,request,response);
        return authentication.isAuthenticated();
    }
}
