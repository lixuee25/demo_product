package com.products.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
//import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.products.dto.request.LoginRequest;
import com.products.dto.request.SignupRequest;
import com.products.dto.response.JwtResponse;
import com.products.dto.response.MessageResponse;
import com.products.model.Role;
import com.products.model.User;
import com.products.repository.RoleRepository;
import com.products.repository.UserRepository;
import com.products.service.UserService;
//import com.products.service.impl.UserDetailsImpl;
//import com.products.service.impl.UserDetailsServiceImpl;
import com.products.utils.JwtUtils;

import jakarta.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")


public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;
//
//    @Autowired
//    UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @PostMapping("/signin")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            logger.debug("Đang xác thực người dùng: {}", loginRequest.getUsername());
            
            UserDetails userDetails = null;       
            
            UsernamePasswordAuthenticationToken uAuthenticationToken = new UsernamePasswordAuthenticationToken(
            		loginRequest,loginRequest.getPassword());

            if(userDetails == null) {
            	logger.error("Không tìm thấy tên người dùng và mật khẩu", loginRequest.getUsername());
            	 return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new MessageResponse("Error: Taì khoản không tồn tại."));
            }
            
            PasswordEncoder encoder = new BCryptPasswordEncoder();
            if(!encoder.matches(loginRequest.getPassword(), userDetails.getPassword())  ) {
            	logger.error("Sai mật khẩu ", loginRequest.getUsername());
            	return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new MessageResponse("Error: Sai password."));
            }
            
            
//            UserDetailsImpl userDetails = (UserDetailsImpl) userDetailsService.loadUserByUsername(loginRequest.getUsername());
//            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginRequest, userDetails);
//            authenticationToken.setAuthenticated(userDetails);
            Authentication authentication = authenticationManager.authenticate(uAuthenticationToken);
            
            SecurityContextHolder.getContext().setAuthentication(authentication);
//            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            String jwt = jwtUtils.generateJwtToken(userDetails);

            List<String> roles = userDetails.getAuthorities().stream()
                    .map(item -> item.getAuthority())
                    .collect(Collectors.toList());

            logger.debug("Xác thực thành công cho người dùng: {}", userDetails.getUsername());
            return ResponseEntity.ok(new JwtResponse(jwt,
                    ((JwtResponse) userDetails).getId(),
                    userDetails.getUsername(),
                    ((SignupRequest) userDetails).getEmail(),
                    roles));
        } catch (Exception e) {
            logger.error("Lỗi đăng nhập cho người dùng: {}", loginRequest.getUsername(), e);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new MessageResponse("Error: Sai tên đăng nhập hoặc mật khẩu."));
        }
    }
    
    
    
    @PostMapping("/signup")
    public ResponseEntity<?> register(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Lỗi: Username đã được sử dụng!"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Lỗi: Email đã được sử dụng!"));
        }

      
        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword())); 

        Set<String> asignRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (asignRoles == null || asignRoles.isEmpty()) {
            Role userRole = roleRepository.findByName("USER")
                    .orElseThrow(() -> new RuntimeException("Lỗi: Role 'USER' không tìm thấy."));
            roles.add(userRole);
        } else {
            asignRoles.forEach(role -> {
                switch (role.toLowerCase()) {
                    case "admin":
                        Role adminRole = roleRepository.findByName("ADMIN")
                                .orElseThrow(() -> new RuntimeException("Lỗi: Role 'ADMIN' không tìm thấy."));
                        roles.add(adminRole);
                        break;
                    default:
                        Role userRole = roleRepository.findByName("USER")
                                .orElseThrow(() -> new RuntimeException("Lỗi: Role 'USER' không tìm thấy."));
                        roles.add(userRole);
                        break;
                }
            });
        }

        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User đăng ký thành công!"));
    }
}
    
//    @PostMapping("/signup")
//    public ResponseEntity<?> register(@Valid @RequestBody SignupRequest signUpRequest) {
//        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
//            return ResponseEntity
//                    .badRequest()
//                    .body(new MessageResponse("Error: Username is already taken!"));
//        }
//
//        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
//            return ResponseEntity
//                    .badRequest()
//                    .body(new MessageResponse("Error: Email is already in use!"));
//        }
//
//      
//        User user = new User(signUpRequest.getUsername(), signUpRequest.getEmail(),
//                encoder.encode(signUpRequest.getPassword()));
//
//        Set<String> asignRoles = signUpRequest.getRole();
//        Set<Role> roles = new HashSet<>();
//
//        if (asignRoles == null || asignRoles.isEmpty()) {
//           
//            Role userRole = roleRepository.findByName("USER")
//                    .orElseThrow(() -> new RuntimeException("Error: Role 'USER' is not found."));
//            roles.add(userRole);
//        } else {
//            
//            asignRoles.forEach(role -> {
//                switch (role.toLowerCase()) {
//                    case "admin":
//                        Role adminRole = roleRepository.findByName("ADMIN")
//                                .orElseThrow(() -> new RuntimeException("Error: Role 'ADMIN' is not found."));
//                        roles.add(adminRole);
//                        break;
//                    case "mod":
//                        Role modRole = roleRepository.findByName("MODERATOR")
//                                .orElseThrow(() -> new RuntimeException("Error: Role 'MODERATOR' is not found."));
//                        roles.add(modRole);
//                        break;
//                    default:
//                        Role userRole = roleRepository.findByName("USER")
//                                .orElseThrow(() -> new RuntimeException("Error: Role 'USER' is not found."));
//                        roles.add(userRole);
//                        break;
//                }
//            });
//        }
//
//        user.setRoles(roles);
//        userRepository.save(user);
//
//        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
//    }
//}
    
    
    
//    
//@PostMapping("/signin")
//public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
//    try {
//        logger.debug("Đang xác thực người dùng: {}", loginRequest.getUsername());
//        
//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
//        
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
//        String jwt = jwtUtils.generateJwtToken(userDetails);
//
//        List<String> roles = userDetails.getAuthorities().stream()
//                .map(item -> item.getAuthority())
//                .collect(Collectors.toList());
//
//        logger.debug("Xác thực thành công cho người dùng: {}", userDetails.getUsername());
//        return ResponseEntity.ok(new JwtResponse(jwt,
//                userDetails.getId(),
//                userDetails.getUsername(),
//                userDetails.getEmail(),
//                roles));
//    } catch (Exception e) {
//        logger.error("Lỗi đăng nhập cho người dùng: {}", loginRequest.getUsername(), e);
//        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new MessageResponse("Error: Sai tên đăng nhập hoặc mật khẩu."));
//    }
//}

    
    
//@PostMapping("/signin")
//public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
//    Authentication authentication = authenticationManager.authenticate(
//            new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
//
//    SecurityContextHolder.getContext().setAuthentication(authentication);
//    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
//    String jwt = jwtUtils.generateJwtToken(userDetails);
//
//    List<String> roles = userDetails.getAuthorities().stream()
//            .map(item -> item.getAuthority())
//            .collect(Collectors.toList());
//
//    return ResponseEntity.ok(new JwtResponse(jwt,
//            userDetails.getId(),
//            userDetails.getUsername(),
//            userDetails.getEmail(),
//            roles));
//}
    
    
    
//
//    @PostMapping("/signup")
//    public ResponseEntity<?> register(@Valid @RequestBody SignupRequest signUpRequest) {
//        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
//            return ResponseEntity
//                    .badRequest()
//                    .body(new MessageResponse("Error: Username is already taken!"));
//        }
//
//        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
//            return ResponseEntity
//                    .badRequest()
//                    .body(new MessageResponse("Error: Email is already in use!"));
//        }
//
//        User user = new User(signUpRequest.getUsername(), signUpRequest.getEmail(),
//                            encoder.encode(signUpRequest.getPassword()));
//
//        Set<String> asignRoles = signUpRequest.getRole();
//        Set<Role> roles = new HashSet<>();
//
//        if (asignRoles == null) {
//            Role userRole = roleRepository.findByName(RoleEnum.ROLE_USER.getRole())
//                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//            roles.add(userRole);
//        } else {
//            asignRoles.forEach(role -> {
//                switch (role) {
//                    case "admin":
//                        Role adminRole = roleRepository.findByName(RoleEnum.ROLE_ADMIN.getRole())
//                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//                        roles.add(adminRole);
//
//                        break;
//                    case "mod":
//                        Role modRole = roleRepository.findByName(RoleEnum.ROLE_MODERATOR.getRole())
//                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//                        roles.add(modRole);
//
//                        break;
//                    default:
//                        Role userRole = roleRepository.findByName(RoleEnum.ROLE_USER.getRole())
//                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//                        roles.add(userRole);
//                }
//            });
//        }
//
//        user.setRoles(roles);
//        userRepository.save(user);
//
//        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
//    }
//}