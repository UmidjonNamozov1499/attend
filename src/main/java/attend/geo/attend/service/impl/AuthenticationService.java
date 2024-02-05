package attend.geo.attend.service.impl;

import attend.geo.attend.config.JwtService;
import attend.geo.attend.constants.Role;
import attend.geo.attend.constants.TokenType;
import attend.geo.attend.dto.RegisterRequest;
import attend.geo.attend.dto.authentication.AuthenticationRequest;
import attend.geo.attend.entity.Token;
import attend.geo.attend.entity.Users;
import attend.geo.attend.repository.TokenRepository;
import attend.geo.attend.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
  private final UsersRepository repository;
  private final TokenRepository tokenRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;

  public HttpEntity<?> register(RegisterRequest request) {
    if (request.getUserName() == null && request.getPassword() == null) {
     return ResponseEntity.ok().body("UserName or password error");
    }

    var user = Users.builder()
        .userName(request.getUserName())
        .password(passwordEncoder.encode(request.getPassword()))
        .role(Role.ADMIN)
        .build();
    Users savedUser = repository.save(user);
    String jwtToken = jwtService.generateToken(user);
    saveUserToken(savedUser, jwtToken);

    return ResponseEntity.ok(jwtToken);
  }

  public HttpEntity<?> authenticate(AuthenticationRequest request) {
    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUserName(), request.getPassword())
    );
    Users user = repository.findByUserName(request.getUserName())
        .orElseThrow();
    String jwtToken = jwtService.generateToken(user);
    saveUserToken(user, jwtToken);
    return ResponseEntity.ok().body("Success");
  }

  private void saveUserToken(Users user, String jwtToken) {
    var token = Token.builder()
        .user(user)
        .token(jwtToken)
        .tokenType(TokenType.BEARER)
        .build();
    tokenRepository.save(token);
  }
}
