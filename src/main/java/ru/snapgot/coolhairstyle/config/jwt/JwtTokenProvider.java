package ru.snapgot.coolhairstyle.config.jwt;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Component
public class JwtTokenProvider {

    private final UserDetailsService userDetailsService;

    @Autowired
    public JwtTokenProvider(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Value("${jwt.secret}")
    private String secretKey;
    @Value("${jwt.expirationMs}")
    private long expirationTime;

    @PostConstruct
    protected void unit(){
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public Map<String, String> createTokens(String username, String password){
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("password", password);
        Date now = new Date();
        Map<String, String> tokens = new HashMap<>();
        tokens.put("accessToken", Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + expirationTime))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact());
        tokens.put("refreshToken", Jwts.builder()
                .setClaims(Jwts.claims().setSubject(username))
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + expirationTime * 30))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact());
        return tokens;
    }

    public String createAccessToken(String username, String password){
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("password", password);
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + expirationTime))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public boolean validateToken(String token){
        try {
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return !claimsJws.getBody().getExpiration().before(new Date());
        } catch (JwtException | IllegalArgumentException e){
            throw new JwtAuthenticationException("Jwt token invalid or expired", HttpStatus.UNAUTHORIZED) {
            };
        }
    }

    public Authentication getAuthentication(String token){
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(getUsername(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getUsername(String token){
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    public String resolveToken(HttpServletRequest request){
        return request.getHeader(AUTHORIZATION);
    }
}
