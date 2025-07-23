package com.apiserverapplication.backend.service;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.apiserverapplication.backend.model.AuthRequest;

import javax.crypto.SecretKey;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class AuthService {

	private static final SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private final Map<String, String> users = new ConcurrentHashMap<>();
    private static final Logger logger = LogManager.getLogger(AuthService.class);

    public ResponseEntity<String> register(AuthRequest req) {
        if (users.containsKey(req.getUsername())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User already exists");
        }
        users.put(req.getUsername(), req.getPassword());
        return ResponseEntity.ok("User registered successfully");
    }

    public ResponseEntity<String> login(AuthRequest req) {
        if (!users.containsKey(req.getUsername()) || !users.get(req.getUsername()).equals(req.getPassword())) {
            logger.warn("Invalid credentials provided");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
        String jwt = Jwts.builder().setSubject(req.getUsername()).signWith(key).compact();
        return ResponseEntity.ok(jwt);
    }

    public static String getUserFromToken(String tokenHeader) {
        try {
            if (!tokenHeader.startsWith("Bearer ")) return null;
            String token = tokenHeader.substring(7);
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getSubject();
        } catch (Exception e) {
            return null;
        }
    }
}
