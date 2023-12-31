package com.bestteam.urlshorter.auth;

import com.bestteam.urlshorter.exception.UserExistException;
import com.bestteam.urlshorter.repository.UserUrlRepository;
import com.bestteam.urlshorter.service.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;

@RestController
@RequestMapping(path = "/api/v1/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final UserUrlRepository userUrlRepository;

    public AuthenticationController(@Lazy AuthenticationService authenticationService, UserUrlRepository userUrlRepository) {
        this.authenticationService = authenticationService;
        this.userUrlRepository = userUrlRepository;
    }

    @PostMapping("/registration")
    public ResponseEntity<?> register(
            @Valid @RequestBody RegistrationRequest request
    ) {
        if (userUrlRepository.findByUsername(request.getUsername()).isPresent()) {
            return new ResponseEntity<>(new UserExistException("Username: " + request.getUsername() + " is exist!"), HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<AuthenticationResponse> refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        AuthenticationResponse authenticationResponse = authenticationService.refreshToken(request, response);
        return ResponseEntity.ok(authenticationResponse);
    }
}