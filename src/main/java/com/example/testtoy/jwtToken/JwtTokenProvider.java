package com.example.testtoy.jwtToken;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Component
public class JwtTokenProvider {
    @Value("${jwt.secret}")
    private String secretKey;

    private final long validityInMilliseconds = 180000; // 1시간

    // JWT 토큰 생성
    public String createToken(String username, String role) {
        // role에 "ROLE_" 접두어를 붙여줍니다.
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("role", "ROLE_" + role); // "ROLE_" 접두어 추가
        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);
        // SecretKey를 사용하여 Key 객체를 생성
        Key key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
        // signWith 대신 Keys.hmacShaKeyFor로 생성된 key를 사용
        try {
            String jwtsConst = Jwts.builder()
                    .setClaims(claims)
                    .setIssuedAt(now)
                    .setExpiration(validity)
                    .signWith(key, SignatureAlgorithm.HS512)  // Key 객체와 알고리즘을 지정
                    .compact();
            return jwtsConst;
        } catch (Exception e) {
            System.out.println("JWT 생성 중 예외 발생: " + e.getMessage());
            e.printStackTrace();
            return null;  // 예외가 발생하면 null 반환 (혹은 다른 처리를 해줄 수 있음)
        }
    }

    // 토큰에서 사용자 이름 가져오기
    public String getUsername(String token) {
        return Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes())).build()
                .parseClaimsJws(token).getBody().getSubject();
    }

    // 토큰에서 권한 가져오기
    public List<GrantedAuthority> getAuthorities(String token) {
        Claims claims = Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes())).build()
                .parseClaimsJws(token).getBody();
        String role = claims.get("role", String.class);

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(role));

        return authorities;
    }

    // 인증 객체 생성
    public Authentication getAuthentication(String token) {
        String username = getUsername(token);
        List<GrantedAuthority> authorities = getAuthorities(token);

        // Spring Security의 User 객체 생성
        User user = new User(username, "", authorities);

        // 인증 객체 반환
        return new UsernamePasswordAuthenticationToken(user, token, authorities);
    }

    // 토큰 검증
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes())).build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}