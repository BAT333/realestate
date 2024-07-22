package com.example.realestate.controller;

import com.example.realestate.config.token.TokenService;
import com.example.realestate.domain.User;
import com.example.realestate.domain.UserRole;
import com.example.realestate.model.DataLoginDTO;
import com.example.realestate.model.DataToken;
import com.example.realestate.repository.UserRepository;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("auth")
@SecurityRequirement(name = "bearer-key")
public class UserController {
    @Autowired
    private AuthenticationManager manager;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public ResponseEntity<DataToken> login(@RequestBody DataLoginDTO dto){
        var user = new UsernamePasswordAuthenticationToken(dto.login(),dto.password());
        var userAuth = manager.authenticate(user);
        return ResponseEntity.ok(new DataToken(tokenService.generatesTokens((User) userAuth.getPrincipal())));
    }
    @PostMapping("/register")
    @Transactional
    public ResponseEntity<DataLoginDTO> register(@RequestBody DataLoginDTO dto, UriComponentsBuilder builder){
        if(userRepository.existsByLogin(dto.login())) throw new RuntimeException("ERRO REGISTER");
        var user = userRepository.save(new User(dto.login(),this.encoder(dto.password()), UserRole.ADMIN));
        return ResponseEntity.ok(new DataLoginDTO(user.getLogin(), user.getPasswords()));
    }

    private String encoder(String password) {
        return new BCryptPasswordEncoder().encode(password);
    }
}
