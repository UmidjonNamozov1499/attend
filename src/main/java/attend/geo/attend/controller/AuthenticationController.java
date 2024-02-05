package attend.geo.attend.controller;

import attend.geo.attend.dto.RegisterRequest;
import attend.geo.attend.dto.authentication.AuthenticationRequest;
import attend.geo.attend.service.impl.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;

    @PostMapping("/register")
    public HttpEntity<?> register(@RequestBody RegisterRequest request) {
        return service.register(request);
    }

    @PostMapping("/authenticate")
    public HttpEntity<?> authenticate(@RequestBody AuthenticationRequest request) {
        return service.authenticate(request);
    }
}
