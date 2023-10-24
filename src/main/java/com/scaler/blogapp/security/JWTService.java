package com.scaler.blogapp.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JWTService {
    // TODO move the key to a separate .property file
    private static final String JWT_Key = "jh23jbj12b21b2bcb8b8bb";
    Algorithm algorithm = Algorithm.HMAC256(JWT_Key);

    public String createJwt(Long userId) {
        return JWT.create()
                .withSubject(userId.toString())
                .withIssuedAt(new Date())
                //.withExpiresAt()
                .sign(algorithm);
    }
}
