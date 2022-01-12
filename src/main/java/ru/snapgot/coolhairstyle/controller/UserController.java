package ru.snapgot.coolhairstyle.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;
import ru.snapgot.coolhairstyle.model.dto.AuthenticationRequestDto;
import ru.snapgot.coolhairstyle.model.User;
import ru.snapgot.coolhairstyle.repos.UserRepository;
import ru.snapgot.coolhairstyle.config.jwt.JwtAuthenticationException;
import ru.snapgot.coolhairstyle.config.jwt.JwtTokenProvider;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/")
public class UserController {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public UserController(UserRepository userRepository,
                          PasswordEncoder passwordEncoder,
                          JwtTokenProvider jwtTokenProvider,
                          AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("sign_up")
    public void createUser(@RequestBody User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @PostMapping("login")
    public ResponseEntity<?> loginUser(@RequestBody AuthenticationRequestDto request){
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
            Map<String, String> tokens = jwtTokenProvider.createTokens(request.getUsername(), request.getPassword());
            Map<Object, Object> response = new HashMap<>();
            response.put("username", request.getUsername());
            response.put("access_token", tokens.get("accessToken"));
            response.put("refresh_token", tokens.get("refreshToken"));
            return ResponseEntity.ok(response);
        } catch (AuthenticationException e){
            return new ResponseEntity<>("Invalid username/password combination", HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("refresh_token")
    public ResponseEntity<?> refreshToken(ServletRequest servletRequest,
                                          ServletResponse servletResponse) throws IOException {
        String token = jwtTokenProvider.resolveToken((HttpServletRequest) servletRequest);
        try{
            if (token != null && jwtTokenProvider.validateToken(token)){
                User user = userRepository.findByUsername(jwtTokenProvider.getUsername(token));
                String accessToken = jwtTokenProvider.createAccessToken(user.getUsername(), user.getPassword());
                Map<Object, Object> response = new HashMap<>();
                response.put("access_token", accessToken);
                return ResponseEntity.ok(response);
            }
        } catch (JwtAuthenticationException e){
            SecurityContextHolder.clearContext();
            ((HttpServletResponse) servletResponse).sendError(e.getHttpStatus().value());
            throw new JwtAuthenticationException("Jwt token invalid or expired");
        }
        return null;
    }

    @PostMapping("logout")
    public void logoutUser(HttpServletResponse response, HttpServletRequest request){
        SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
        securityContextLogoutHandler.logout(request, response, null);
    }

    @GetMapping("test")
    public ResponseEntity<?> test(){
        Map<Object, Object> test = new HashMap<>();
        test.put("test", "Good request");
        return ResponseEntity.ok(test);
    }
}
