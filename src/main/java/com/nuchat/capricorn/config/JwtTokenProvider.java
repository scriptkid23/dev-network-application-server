package com.nuchat.capricorn.config;

import java.io.Serializable;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import com.nuchat.capricorn.dto.RevokeTokenDTO;
import com.nuchat.capricorn.exception.CustomException;
import com.nuchat.capricorn.model.RevokeToken;
import com.nuchat.capricorn.model.Role;
import com.nuchat.capricorn.repository.RevokeTokenRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


@Component
public class JwtTokenProvider{

    /**
     * THIS IS NOT A SECURE PRACTICE! For simplicity, we are storing a static key here. Ideally, in a
     * microservices environment, this key would be kept on a config-server.
     */

    @Value("${security.jwt.token.secret-key:secret-key}")
    private static final String secretKey = "sec324ret234ke5fg6yf7dgo5fhg6fh43m5e";

    @Value("${security.jwt.token.expire-length:3600000}")
    private static final long validityInMilliseconds = 86400000L; // 24h

    @Autowired
    private MyUserDetails myUserDetails;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    private RevokeTokenRepository revokeTokenRepository;

    private static final Logger logger = LoggerFactory.getLogger(WebSocketConfig.class);

    public String createToken(String username, List<Role> roles) {

        Claims claims = Jwts.claims().setSubject(username);
        claims.put("auth", roles.stream().map(s -> new SimpleGrantedAuthority(s.getAuthority())).filter(Objects::nonNull).collect(Collectors.toList()));

        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);

        return Jwts.builder()//
                .setClaims(claims)//
                .setIssuedAt(now)//
                .setExpiration(validity)//
                .signWith(SignatureAlgorithm.HS256, secretKey)//
                .compact();
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = myUserDetails.loadUserByUsername(getUsername(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getUsername(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    public String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public boolean validateToken(String token) {
        try {
            if(revokeTokenRepository.existsByToken(token)){
                throw new CustomException("Token has been revoke",HttpStatus.INTERNAL_SERVER_ERROR);
            }
            else{
                Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
                return true;
            }

        } catch (JwtException | IllegalArgumentException e) {
            throw new CustomException("Expired or invalid JWT token", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
