package ru.otus.filinovich.authenticationservice.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.filinovich.authenticationservice.models.ERole;
import ru.otus.filinovich.authenticationservice.models.Role;
import ru.otus.filinovich.authenticationservice.models.User;
import ru.otus.filinovich.authenticationservice.payload.request.LoginRequest;
import ru.otus.filinovich.authenticationservice.payload.request.SignupRequest;
import ru.otus.filinovich.authenticationservice.payload.response.JwtResponse;
import ru.otus.filinovich.authenticationservice.payload.response.MessageResponse;
import ru.otus.filinovich.authenticationservice.payload.response.UserInfo;
import ru.otus.filinovich.authenticationservice.repository.RoleRepository;
import ru.otus.filinovich.authenticationservice.repository.UserRepository;
import ru.otus.filinovich.authenticationservice.security.jwt.JwtUtils;
import ru.otus.filinovich.authenticationservice.security.services.UserDetailsImpl;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
  private final AuthenticationManager authenticationManager;

  private final UserRepository userRepository;

  private final RoleRepository roleRepository;

  private final PasswordEncoder encoder;

  private final JwtUtils jwtUtils;

  @PostMapping("/signin")
  public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);
    String jwt = jwtUtils.generateJwtToken(authentication);
    
    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
    Optional<User> userOptional = userRepository.findByUsername(userDetails.getUsername());
    if (userOptional.isPresent() && userOptional.get().isBanned()) {
      return ResponseEntity.status(403).build();
    }
    List<String> roles = userDetails.getAuthorities().stream()
        .map(item -> item.getAuthority())
        .collect(Collectors.toList());
    UserInfo userInfo = new UserInfo(userDetails.getUsername(), userDetails.getEmail());
    return ResponseEntity.ok(new JwtResponse(jwt, 
                         userDetails.getId(),
                         userInfo,
                         roles, userDetails.getBooks()));
  }

  @PostMapping("/signup")
  public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
    if (userRepository.existsByUsername(signUpRequest.getUsername())) {
      return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
    }
    if (userRepository.existsByEmail(signUpRequest.getEmail())) {
      return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
    }
    User user = new User(signUpRequest.getUsername(), signUpRequest.getEmail(),
               encoder.encode(signUpRequest.getPassword()));
    Set<String> strRoles = signUpRequest.getRole();
    Set<Role> roles = new HashSet<>();
    if (strRoles == null) {
      Role userRole = roleRepository.findByName(ERole.ROLE_USER)
          .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
      roles.add(userRole);
    } else {
      strRoles.forEach(role -> {
        Role role1 = getRole(role);
        roles.add(role1);
      });
    }
    user.setRoles(roles);
    userRepository.save(user);
    return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
  }

  private Role getRole(String role) {
    switch (role) {
      case "admin":
        return roleRepository.findByName(ERole.ROLE_ADMIN)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
      case "mod":
        return roleRepository.findByName(ERole.ROLE_MODERATOR)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
      default:
        return roleRepository.findByName(ERole.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
    }
  }
}
